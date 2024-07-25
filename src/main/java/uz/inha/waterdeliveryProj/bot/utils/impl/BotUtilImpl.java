package uz.inha.waterdeliveryProj.bot.utils.impl;

import com.pengrad.telegrambot.model.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.constants.BotConstant;
import uz.inha.waterdeliveryProj.bot.entity.BottleType;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.service.BottleService;
import uz.inha.waterdeliveryProj.bot.service.DeliveryTimeService;
import uz.inha.waterdeliveryProj.bot.service.RegionService;
import uz.inha.waterdeliveryProj.bot.utils.BotUtil;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BotUtilImpl implements BotUtil {
    private final RegionService regionService;
    private final BottleService bottleService;
    private final DeliveryTimeService deliveryTimeService;
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
    public Keyboard generateDeliveryScheduleBtns(TelegramUser tgUser, List<DeliveryTime> times) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> btns = new LinkedList<>();
        for (int i = 1; i < times.size(); i++) {
            DeliveryTime deliveryTime = times.get(i);
            if(deliveryTimeService.canFitToday(deliveryTime, tgUser) && deliveryTimeService.deliveryTimeIsNow(deliveryTime)){
                btns.add(new InlineKeyboardButton(
                        deliveryTime.toString()
                ).callbackData(deliveryTime.getId().toString()));
            }
        }
        inlineKeyboardMarkup.addRow(btns.toArray(value -> new InlineKeyboardButton[0]));
        return inlineKeyboardMarkup;
    }
}
