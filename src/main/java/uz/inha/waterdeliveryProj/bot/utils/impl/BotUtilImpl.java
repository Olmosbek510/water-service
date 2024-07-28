package uz.inha.waterdeliveryProj.bot.utils.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.constants.BotConstant;
import uz.inha.waterdeliveryProj.bot.entity.BottleType;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.service.BottleService;
import uz.inha.waterdeliveryProj.bot.service.DeliveryTimeService;
import uz.inha.waterdeliveryProj.bot.service.RegionService;
import uz.inha.waterdeliveryProj.bot.service.TelegramUserService;
import uz.inha.waterdeliveryProj.bot.utils.BotUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BotUtilImpl implements BotUtil {
    private final RegionService regionService;
    private final BottleService bottleService;
    private final DeliveryTimeService deliveryTimeService;
    private final TelegramBot telegramBot;
    private final TelegramUserService telegramUserService;

    @Override
    public Keyboard generateContactButton() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton(BotConstant.SHARE_CONTACT).requestContact(true)
        ).resizeKeyboard(true);
    }

    @Override
    public Keyboard generateLocationButton() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton(BotConstant.SHARE_LOCATION).requestLocation(true)
        ).resizeKeyboard(true);
    }

    @Override
    public Keyboard generateRegionButtons() {
        List<Region> regions = regionService.findAll();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        for (int i = 0; i < regions.size() - 1; i += 2) {
            inlineKeyboardMarkup.addRow(
                    new InlineKeyboardButton(regions.get(i).getName()).callbackData(regions.get(i).getId().toString()),
                    new InlineKeyboardButton(regions.get(i + 1).getName()).callbackData(regions.get(i + 1).getId().toString())

            );
        }
        return inlineKeyboardMarkup;
    }

    @Override
    public Keyboard generateOrderBtns() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(BotConstant.ORDER_BTN).callbackData(BotConstant.START_ORDERING)
        );
    }

    @Override
    public Keyboard generateBottleTypesBtns() {
        List<BottleType> activeBottles = bottleService.findActives();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        IntStream.range(0, activeBottles.size() - 1)
                .forEach(i -> inlineKeyboardMarkup.addRow(
                        new InlineKeyboardButton(activeBottles.get(i).getType()).callbackData(activeBottles.get(i).getId().toString()),
                        new InlineKeyboardButton(activeBottles.get(i + 1).getType()).callbackData(activeBottles.get(i + 1).getId().toString())));
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup generateBottleNumberButtons(TelegramUser tgUser) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("+").callbackData(BotConstant.PLUS),
                new InlineKeyboardButton(tgUser.getBottleCount().toString()).callbackData("number"),
                new InlineKeyboardButton("-").callbackData(BotConstant.MINUS)
        );
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(
                BotConstant.CONFIRM_BTN
        ).callbackData(BotConstant.CONFIRM));
        return inlineKeyboardMarkup;
    }

    @Override
    public Keyboard generateConfirmBtn() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(
                BotConstant.CONFIRM
        ).callbackData(BotConstant.CONFIRM));
        return inlineKeyboardMarkup;
    }

    @Override
    public void generateDeliveryScheduleBtns(TelegramUser tgUser) {
        List<LocalDate> days = deliveryTimeService.generateDays();
        List<DeliveryTime> times = deliveryTimeService.findAll();
        for (LocalDate day : days) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            SendMessage sendMessage = new SendMessage(tgUser.getChatId(), deliveryTimeService.getDayAsText(day));
            List<InlineKeyboardButton> btns = new ArrayList<>();
            for (int i = 0; i < times.size(); i++) {
                if (i == 0 && day.equals(LocalDate.now()) || day.equals(LocalDate.now()) && LocalTime.now().isAfter(times.get(i).getStartTime())) {
                    continue;
                }
                DeliveryTime deliveryTime = times.get(i);
                btns.add(new InlineKeyboardButton(
                        deliveryTime.toString()
                ).callbackData(day + "/" + deliveryTime.getId().toString()));
            }
            inlineKeyboardMarkup.addRow(btns.toArray(value -> new InlineKeyboardButton[0]));
            sendMessage.replyMarkup(inlineKeyboardMarkup);
            telegramBot.execute(sendMessage);
            tgUser.setState(TelegramState.CONFIRM_ORDER);
            telegramUserService.save(tgUser);
        }
    }

    @Override
    public Keyboard generateCheckoutButton() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(BotConstant.CONFIRM_BTN).callbackData(BotConstant.CONFIRM_ORDER),
                new InlineKeyboardButton(BotConstant.CANCEL).callbackData(BotConstant.CANCEL)
        );
    }

    @Override
    public String generateOrderText(TelegramUser tgUser) {
        return
                BotConstant.ORDER_INFO
               .formatted(
                tgUser.getBottleType().getType(),
                tgUser.getBottleCount(),
                tgUser.calculateTotalPriceOfCurrentOrder(),
                tgUser.getCurrentOrderDay(),
                tgUser.getCurrentOrderDeliveryTime().toString()
        );
    }
}
