package uz.inha.waterdeliveryProj.bot.utils;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface BotUtil {
    Keyboard generateContactButton();
    Keyboard generateLocationButton();

    Keyboard generateRegionButtons();
}
