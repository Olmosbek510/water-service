package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.District;
import uz.inha.waterdeliveryProj.bot.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> findAllByTelegramUserDistrictAndDeliveryTimeAndDay(District district, DeliveryTime deliveryTime, LocalDate now);
}
