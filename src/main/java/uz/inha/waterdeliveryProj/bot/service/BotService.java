package uz.inha.waterdeliveryProj.bot.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Message;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;

public interface BotService {
    TelegramUser getOrCreateTelegramUser(Long chatId);

    void acceptStartAskContact(Message message, TelegramUser tgUser);

    void acceptContactSendRegion(Contact contact, TelegramUser tgUser);

    void acceptRegionSendDistrictButtons(CallbackQuery callbackQuery, TelegramUser tgUser);

    void acceptRegionSendLocation(CallbackQuery callbackQuery, TelegramUser tgUser);

    void acceptLocationAndWait(Location location, TelegramUser tgUser);

    void sendLocationButton(TelegramUser tgUser);
}
