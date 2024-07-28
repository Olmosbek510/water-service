package uz.inha.waterdeliveryProj.bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.inha.waterdeliveryProj.bot.constants.BotConstant;
import uz.inha.waterdeliveryProj.bot.entity.*;
import uz.inha.waterdeliveryProj.bot.entity.enums.OrderStatus;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.model.request.VerifyUserDTO;
import uz.inha.waterdeliveryProj.bot.service.*;
import uz.inha.waterdeliveryProj.bot.utils.BotUtil;
import uz.inha.waterdeliveryProj.bot.utils.PhoneRepairUtil;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {
    private final TelegramUserService telegramUserService;
    private final BotUtil botUtil;
    private final TelegramBot telegramBot;
    private final PhoneRepairUtil phoneRepairUtil;
    private final UserService userService;
    private final DistrictService districtService;
    private final RegionService regionService;
    private final BottleService bottleService;
    private final DeliveryTimeService deliveryTimeService;
    private final OrderService orderService;

    @Override
    public TelegramUser getOrCreateTelegramUser(Long chatId) {
        Optional<TelegramUser> telegramUserOpt = telegramUserService.findByChatId(chatId);
        if (telegramUserOpt.isPresent()) {
            return telegramUserOpt.get();
        } else {
            TelegramUser telegramUser = TelegramUser.builder()
                    .orderCount(1)
                    .chatId(chatId)
                    .bottleCount(2)
                    .state(TelegramState.START)
                    .build();
            return telegramUserService.save(telegramUser);
        }
    }

    @Override
    public void acceptStartAskContact(Message message, TelegramUser tgUser) {
        SendMessage sendMessage = new SendMessage(message.chat().id(), BotConstant.PLEASE_SHARE_CONTACT);
        sendMessage.replyMarkup(botUtil.generateContactButton());
        telegramBot.execute(sendMessage);
        tgUser.setState(TelegramState.SHARE_CONTACT);
        telegramUserService.save(tgUser);
    }

    @Override
    public void acceptContactSendRegion(Contact contact, TelegramUser tgUser) {
        String phone = phoneRepairUtil.repairPhone(contact.phoneNumber());
        User user = User.builder()
                .phone(phone)
                .build();
        userService.save(user);
        tgUser.setUser(user);
        tgUser.setState(TelegramState.SELECT_REGION);
        telegramBot.execute(new SendMessage(tgUser.getChatId(), BotConstant.REGIONS));
        SendMessage message = new SendMessage(tgUser.getChatId(), BotConstant.SELECT_REGION);
        message.replyMarkup(botUtil.generateRegionButtons());
        SendResponse execute = telegramBot.execute(message);
        Integer messageId = execute.message().messageId();
        tgUser.setDeletingMessage(messageId);
        telegramUserService.save(tgUser);
    }

    @Override
    public void acceptRegionSendDistrictButtons(CallbackQuery callbackQuery, TelegramUser tgUser) {
        UUID regionId = UUID.fromString(callbackQuery.data());
        districtService.findByRegionId(regionId);

    }

    @Override
    public void acceptRegionSendLocation(CallbackQuery callbackQuery, TelegramUser tgUser) {
        telegramBot.execute(new DeleteMessage(tgUser.getChatId(), tgUser.getDeletingMessage()));
        UUID regionId = UUID.fromString(callbackQuery.data());
        Optional<Region> byId = regionService.findById(regionId);
        byId.ifPresent(tgUser::setRegion);
        sendLocationButton(tgUser);
    }


    @Override
    public void acceptLocationAndWait(com.pengrad.telegrambot.model.Location location, TelegramUser tgUser) {
        tgUser.setLocation(new Location(
                location.latitude(),
                location.longitude()
        ));
        telegramBot.execute(new SendMessage(tgUser.getChatId(), BotConstant.PLEASE_WAIT).replyMarkup(
                new ReplyKeyboardRemove(true)
        ));
        tgUser.setState(TelegramState.WAITING);
        telegramUserService.save(tgUser);
    }

    @Override
    public void sendLocationButton(TelegramUser tgUser) {
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), BotConstant.PLEASE_SHARE_LOCATION);
        sendMessage.replyMarkup(botUtil.generateLocationButton());
        telegramBot.execute(sendMessage);
        tgUser.setState(TelegramState.SHARE_LOCATION);
        telegramUserService.save(tgUser);
    }

    @Override
    public void sendCabinet(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), BotConstant.YOUR_CABINET);
        sendMessage.replyMarkup(botUtil.generateOrderBtns());
        telegramUser.setState(TelegramState.START_ORDERING);
        telegramUserService.save(telegramUser);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void startOrdering(CallbackQuery callbackQuery, TelegramUser tgUser) {
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), BotConstant.SELECT_BOTTLE_TYPE);
        sendMessage.replyMarkup(botUtil.generateBottleTypesBtns());
        tgUser.setState(TelegramState.SELECT_BOTTLE_TYPE);
        telegramUserService.save(tgUser);
        telegramBot.execute(sendMessage);
    }

    @Transactional
    @Override
    public void verifyUser(VerifyUserDTO verifyUserDTO) {
        TelegramUser telegramUser = telegramUserService.findById(verifyUserDTO.getTgUserId());
        telegramUser.setLocation(Location.builder()
                .latitude(verifyUserDTO.getLatitude())
                .longitude(verifyUserDTO.getLongitude())
                .build());
        telegramUser.setAddressLine(verifyUserDTO.getAddressLine());
        telegramUser.setDistrict(districtService.findById(verifyUserDTO.getDistrictId()));
        telegramUser.setVerified(true);
        telegramUser.setState(TelegramState.CABINET);
        sendCabinet(telegramUser);
        telegramUserService.save(telegramUser);
    }

    @Override
    public void acceptBottleTypeShowSelectNumber(CallbackQuery callbackQuery, TelegramUser tgUser) {
        BottleType bottleType = bottleService.findById(Integer.parseInt(callbackQuery.data()));
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), BotConstant.SELECT_BOTTLE_TYPE);
        sendMessage.replyMarkup(botUtil.generateBottleNumberButtons(tgUser));
        Integer messageId = telegramBot.execute(sendMessage).message().messageId();
        tgUser.setEditingMessageId(messageId);
        tgUser.setState(TelegramState.SELECT_BOTTLE_NUMBER);
        tgUser.setBottleType(bottleType);
        telegramUserService.save(tgUser);

//        Text should indicate the price and count
//        SendMessage message = new SendMessage(tgUser.getChatId(), BotConstant.CONFIRM_BTN);
//        message.replyMarkup(botUtil.generateConfirmBtn());
//        telegramBot.execute(message);
    }

    @Override
    public void changeBottleNumber(CallbackQuery callbackQuery, TelegramUser tgUser) {
        BottleType bottleType = tgUser.getBottleType();
        String data = callbackQuery.data();
        switch (data) {
            case BotConstant.CONFIRM -> showAvailableDeliveryTimes(tgUser);
            case BotConstant.PLUS -> tgUser.setBottleCount(tgUser.getBottleCount() + 2);
            case BotConstant.MINUS -> {
                if (tgUser.getBottleCount() > 2) {
                    tgUser.setBottleCount(tgUser.getBottleCount() - 2);
                }
            }
        }
        telegramUserService.save(tgUser);
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup(
                tgUser.getChatId(),
                tgUser.getEditingMessageId());
        editMessageReplyMarkup.replyMarkup(botUtil.generateBottleNumberButtons(tgUser));
        telegramBot.execute(editMessageReplyMarkup);
    }

    @Override
    public void showAvailableDeliveryTimes(TelegramUser tgUser) {
        showNextThreeDays(tgUser);
    }

    @Override
    public void showNextThreeDays(TelegramUser tgUser) {
        botUtil.generateDeliveryScheduleBtns(tgUser);
    }

    @Override
    public void acceptOrderTimeShowConfirmation(CallbackQuery callbackQuery, TelegramUser tgUser) {
        LocalDate day = deliveryTimeService.extractDayFromCallbackQuery(callbackQuery);
        DeliveryTime deliveryTime = deliveryTimeService.extractDeliveryTimeFromCallbackQuery(callbackQuery);
        tgUser.setCurrentOrderDeliveryTime(deliveryTime);
        tgUser.setCurrentOrderDay(day);
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), botUtil.generateOrderText(tgUser));
        sendMessage.parseMode(ParseMode.HTML);
        sendMessage.replyMarkup(botUtil.generateCheckoutButton());
        tgUser.setState(TelegramState.CREATE_ORDER);
        telegramUserService.save(tgUser);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void makeAnOrder(CallbackQuery callbackQuery, TelegramUser tgUser) {
        if(callbackQuery.data().equals(BotConstant.CANCEL)){
            sendCabinet(tgUser);
            return;
        }
        LocalDate day = tgUser.getCurrentOrderDay();
        DeliveryTime deliveryTime = tgUser.getCurrentOrderDeliveryTime();
        Order order = Order.builder()
                .id(String.valueOf(tgUser.getChatId() + tgUser.getOrderCount()))
                .telegramUser(tgUser)
                .day(day)
                .orderStatus(OrderStatus.CREATED)
                .bottleCount(tgUser.getBottleCount())
                .bottleType(tgUser.getBottleType())
                .deliveryTime(deliveryTime)
                .location(tgUser.getLocation())
                .build();
        tgUser.setOrderCount(tgUser.getOrderCount() + 1);
        telegramUserService.save(tgUser);
        orderService.save(order);
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), BotConstant.ORDER_FINISH_MSG);
        telegramBot.execute(sendMessage);
    }

}
