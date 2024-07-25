package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.service.DistrictService;
import uz.inha.waterdeliveryProj.bot.service.TelegramUserService;
import uz.inha.waterdeliveryProj.repo.TelegramUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final DistrictService districtService;
    @Override
    public Optional<TelegramUser> findByChatId(Long chatId) {
        return telegramUserRepository.findByChatId(chatId);
    }

    @Override
    public TelegramUser save(TelegramUser telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> findByState(TelegramState telegramState) {
        return telegramUserRepository.findByTelegramState(telegramState);
    }

    @Override
    public TelegramUser findByUserId(UUID id) {
        return telegramUserRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("Telegram user not found!!!"));
    }

    @Override
    public TelegramUser findById(UUID id) {
        return telegramUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Telegram user not found!!!"));
    }
}
