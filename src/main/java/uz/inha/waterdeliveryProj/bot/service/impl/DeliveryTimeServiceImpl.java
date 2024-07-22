package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.Order;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.service.DeliveryTimeService;
import uz.inha.waterdeliveryProj.bot.service.OrderService;
import uz.inha.waterdeliveryProj.repo.DeliveryTimeRepository;
import uz.inha.waterdeliveryProj.repo.TelegramUserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryTimeServiceImpl implements DeliveryTimeService {
    private final DeliveryTimeRepository deliveryTimeRepository;
    private final OrderService orderService;
    private final TelegramUserRepository telegramUserRepository;

    @Override
    public List<DeliveryTime> findAll() {
        return deliveryTimeRepository.findAll();
    }

    @Override
    public List<DeliveryTime> saveAll(List<DeliveryTime> deliveryTimes) {
        return deliveryTimeRepository.saveAll(deliveryTimes);
    }

    @Override
    public boolean canFitToday(DeliveryTime deliveryTime, TelegramUser tgUser) {
        List<Order> orders = orderService.findAllByTelegramUserDistrictAndDeliveryTimeAndDay(
                tgUser.getDistrict(),
                deliveryTime,
                LocalDate.now());
        List<Location> locations = orders.stream().map(Order::getLocation).toList();

        return false;
    }
}
