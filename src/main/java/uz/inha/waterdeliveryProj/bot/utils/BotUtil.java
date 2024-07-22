package uz.inha.waterdeliveryProj.bot.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;

public interface BotUtil {
    Keyboard generateContactButton();
    Keyboard generateLocationButton();

    Keyboard generateRegionButtons();

    Keyboard generateOrderBtns();

    Keyboard generateBottleTypesBtns();

    InlineKeyboardMarkup generateBottleNumberButtons(TelegramUser tgUser);

    Keyboard generateConfirmBtn();
}
