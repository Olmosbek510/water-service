package uz.inha.waterdeliveryProj.bot.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.model.Location;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryTimeService {
    Location COMPANY_LOCATION = Location.builder()
            .latitude(41.32662009565237f)
            .longitude( 69.22862417132062f)
            .build();
    List<DeliveryTime> findAll();

    List<DeliveryTime> saveAll(List<DeliveryTime> build);

    boolean canFitToday(DeliveryTime deliveryTime, TelegramUser tgUser);

    boolean deliveryTimeIsNow(DeliveryTime deliveryTime);

    DeliveryTime findById(int i);

    List<LocalDate> generateDays();

    String getDayAsText(LocalDate day);

    LocalDate extractDayFromCallbackQuery(CallbackQuery callbackQuery);

    DeliveryTime extractDeliveryTimeFromCallbackQuery(CallbackQuery callbackQuery);
}
