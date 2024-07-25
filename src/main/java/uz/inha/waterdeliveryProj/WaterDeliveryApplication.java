package uz.inha.waterdeliveryProj;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

@EnableAsync
@SpringBootApplication
public class WaterDeliveryApplication {
//    @Value("${bot.token}")
    private static String botToken = "7282948705:AAEjTuUqvbEj5KaIvrzWJX7XvqsC_3j3EUQ";
    public static void main(String[] args) {
        SpringApplication.run(WaterDeliveryApplication.class, args);
//        stopped in 1:16:22 of the last video 2:02:55
    }

    @Bean
    public TelegramBot telegramBot(){
        return new TelegramBot(botToken);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
