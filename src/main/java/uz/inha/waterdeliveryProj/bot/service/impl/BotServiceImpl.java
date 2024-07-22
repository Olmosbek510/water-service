package uz.inha.waterdeliveryProj.bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.constants.BotConstant;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.User;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.service.*;
import uz.inha.waterdeliveryProj.bot.utils.BotUtil;
import uz.inha.waterdeliveryProj.bot.utils.PhoneRepairUtil;

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

    @Override
    public TelegramUser getOrCreateTelegramUser(Long chatId) {
        Optional<TelegramUser> telegramUserOpt = telegramUserService.findByChatId(chatId);
        if (telegramUserOpt.isPresent()) {
            return telegramUserOpt.get();
        } else {
            TelegramUser telegramUser = TelegramUser.builder()
                    .chatId(chatId)
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
}
