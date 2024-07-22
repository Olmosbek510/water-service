package uz.inha.waterdeliveryProj.bot.utils.impl;

import com.pengrad.telegrambot.model.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.constants.BotConstant;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.service.RegionService;
import uz.inha.waterdeliveryProj.bot.utils.BotUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotUtilImpl implements BotUtil {
    private final RegionService regionService;

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
}
