package uz.inha.waterdeliveryProj.bot.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;

public interface BotUtil {
    Keyboard generateContactButton();

    Keyboard generateLocationButton();

    Keyboard generateRegionButtons();

    Keyboard generateOrderBtns();

    Keyboard generateBottleTypesBtns();

    InlineKeyboardMarkup generateBottleNumberButtons(TelegramUser tgUser);

    Keyboard generateConfirmBtn();

    void generateDeliveryScheduleBtns(TelegramUser tgUser);

    Keyboard generateCheckoutButton();

    String generateOrderText(TelegramUser tgUser);
}
