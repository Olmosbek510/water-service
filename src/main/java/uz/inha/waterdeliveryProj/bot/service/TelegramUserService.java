package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TelegramUserService {
    Optional<TelegramUser> findByChatId(Long chatId);

    TelegramUser save(TelegramUser telegramUser);

    List<TelegramUser> findByState(TelegramState telegramState);

    TelegramUser findByUserId(UUID id);

    TelegramUser findById(UUID id);
}
