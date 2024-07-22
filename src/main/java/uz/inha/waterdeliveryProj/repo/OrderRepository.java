package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;
import uz.inha.waterdeliveryProj.bot.entity.District;
import uz.inha.waterdeliveryProj.bot.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByTelegramUserDistrictAndDeliveryTimeAndDay(District district, DeliveryTime deliveryTime, LocalDate now);
}