package uz.inha.waterdeliveryProj.bot.controller;

import com.pengrad.telegrambot.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.service.BotService;

@Service
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @Async
    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            TelegramUser tgUser = botService.getOrCreateTelegramUser(message.chat().id());
            if (message.text() != null) {
                String text = message.text();
                if (text.equals("/start")) {
                    if (tgUser.isVerified()) {
                        botService.sendCabinet(tgUser);
                    } else {
                        botService.acceptStartAskContact(message, tgUser);
                    }
                }
            } else if (message.contact() != null) {
                if (tgUser.getState().equals(TelegramState.SHARE_CONTACT)) {
                    Contact contact = message.contact();
                    botService.acceptContactSendRegion(contact, tgUser);
                }
            } else if (message.location() != null) {
                if (tgUser.getState().equals(TelegramState.SHARE_LOCATION)) {
                    Location location = message.location();
                    botService.acceptLocationAndWait(location, tgUser);
                }
            }
        } else if (update.callbackQuery() != null) {
            CallbackQuery callbackQuery = update.callbackQuery();
            TelegramUser tgUser = botService.getOrCreateTelegramUser(update.callbackQuery().from().id());
            if (tgUser.getState().equals(TelegramState.SELECT_REGION)) {
                botService.acceptRegionSendLocation(callbackQuery, tgUser);
            } else if (tgUser.checkState(TelegramState.START_ORDERING)) {
                botService.startOrdering(callbackQuery, tgUser);
            } else if (tgUser.checkState(TelegramState.SELECT_BOTTLE_TYPE)) {
                botService.acceptBottleTypeShowSelectNumber(callbackQuery, tgUser);
            } else if (tgUser.checkState(TelegramState.SELECT_BOTTLE_NUMBER)) {
                botService.changeBottleNumber(callbackQuery, tgUser);
            } else if (tgUser.checkState(TelegramState.CONFIRM_ORDER)) {
                botService.acceptOrderTimeShowConfirmation(callbackQuery, tgUser);
            } else if (tgUser.checkState(TelegramState.CREATE_ORDER)) {
                botService.makeAnOrder(callbackQuery, tgUser);
            }
        }
    }
}
