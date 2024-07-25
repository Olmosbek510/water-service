package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.Order;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.service.DeliveryTimeService;
import uz.inha.waterdeliveryProj.bot.service.OrderService;
import uz.inha.waterdeliveryProj.bot.utils.DistanceUtil;
import uz.inha.waterdeliveryProj.repo.DeliveryTimeRepository;
import uz.inha.waterdeliveryProj.repo.TelegramUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryTimeServiceImpl implements DeliveryTimeService {
    private final DeliveryTimeRepository deliveryTimeRepository;
    private final OrderService orderService;
    private final DistanceUtil distanceUtil;

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
        String res = distanceUtil.buildDirectionsApiUrl(locations, COMPANY_LOCATION);
        long totalTimeInSeconds = distanceUtil.getTotalTime(res);
        long totalTimeInMinutes = totalTimeInSeconds / 60;
        totalTimeInMinutes += 15L * locations.size();
        return deliveryTime.getStartTime().plusMinutes(totalTimeInMinutes).isBefore(deliveryTime.getEndTime());
    }

    @Override
    public boolean deliveryTimeIsNow(DeliveryTime deliveryTime) {
        LocalTime now = LocalTime.now();
        return now.isBefore(deliveryTime.getStartTime());
    }

    @Override
    public DeliveryTime findById(int i) {
        return deliveryTimeRepository.findById(i).orElseThrow(() -> new RuntimeException("Delivery Time not found"));
    }
}
