package uz.inha.waterdeliveryProj.bot.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.model.request.VerifyUserDTO;

import java.util.List;

public interface BotService {
    Location COMPANY_LOCATION = Location.builder()
            .latitude(41.32662009565237F)
            .longitude( 69.22862417132062F)
            .build();
    TelegramUser getOrCreateTelegramUser(Long chatId);

    void acceptStartAskContact(Message message, TelegramUser tgUser);

    void acceptContactSendRegion(Contact contact, TelegramUser tgUser);

    void acceptRegionSendDistrictButtons(CallbackQuery callbackQuery, TelegramUser tgUser);

    void acceptRegionSendLocation(CallbackQuery callbackQuery, TelegramUser tgUser);

    void acceptLocationAndWait(com.pengrad.telegrambot.model.Location location, TelegramUser tgUser);

    void sendLocationButton(TelegramUser tgUser);

    void sendCabinet(TelegramUser telegramUser);

    void startOrdering(CallbackQuery callbackQuery, TelegramUser tgUser);

    void verifyUser(VerifyUserDTO verifyUserDTO);

    void acceptBottleTypeShowSelectNumber(CallbackQuery callbackQuery, TelegramUser tgUser);

    void changeBottleNumber(CallbackQuery callbackQuery, TelegramUser tgUser);

    void today(List<DeliveryTime> times, TelegramUser tgUser);
}
