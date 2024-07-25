package uz.inha.waterdeliveryProj.bot.utils;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;

import java.util.List;

public interface BotUtil {
    Keyboard generateContactButton();
    Keyboard generateLocationButton();

    Keyboard generateRegionButtons();

    Keyboard generateOrderBtns();

    Keyboard generateBottleTypesBtns();

    InlineKeyboardMarkup generateBottleNumberButtons(TelegramUser tgUser);

    Keyboard generateConfirmBtn();

    Keyboard generateDeliveryScheduleBtns(TelegramUser tgUser, List<DeliveryTime> times);
}
