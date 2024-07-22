package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, UUID> {
    @Query("select tu from TelegramUser tu where tu.chatId = :chatId")
    Optional<TelegramUser> findByChatId(Long chatId);

    @Query("select tu from TelegramUser tu where tu.state = :telegramState and tu.verified = false order by tu.createdAt")
    List<TelegramUser> findByTelegramState(TelegramState telegramState);

    @Query("select tu from TelegramUser tu where tu.user.id = :userId")
    Optional<TelegramUser> findByUserId(UUID userId);
}