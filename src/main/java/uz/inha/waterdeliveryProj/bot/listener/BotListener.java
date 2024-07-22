package uz.inha.waterdeliveryProj.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import uz.inha.waterdeliveryProj.bot.controller.BotController;

@Component
@RequiredArgsConstructor
public class BotListener implements CommandLineRunner {
    private final TelegramBot telegramBot;
    private final BotController botController;

    @Override
    public void run(String... args) {
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                botController.handleUpdate(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> System.err.println("Error in UpdatesListener: " + e.getMessage()));
    }
}
