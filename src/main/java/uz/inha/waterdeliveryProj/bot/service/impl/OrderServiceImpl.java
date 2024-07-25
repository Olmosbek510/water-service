package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.District;
import uz.inha.waterdeliveryProj.bot.entity.Order;
import uz.inha.waterdeliveryProj.bot.service.OrderService;
import uz.inha.waterdeliveryProj.repo.OrderRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAllByTelegramUserDistrictAndDeliveryTimeAndDay(District district, DeliveryTime deliveryTime, LocalDate now) {
        return orderRepository.findAllByTelegramUserDistrictAndDeliveryTimeAndDay(district, deliveryTime, now);
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        return orderRepository.saveAll(orders);
    }
}
