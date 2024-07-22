package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;

import java.util.List;

public interface DeliveryTimeService {
    List<DeliveryTime> findAll();

    List<DeliveryTime> saveAll(List<DeliveryTime> build);

    boolean canFitToday(DeliveryTime deliveryTime, TelegramUser tgUser);
}
