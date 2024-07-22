package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.inha.waterdeliveryProj.bot.entity.DeliveryTime;

public interface DeliveryTimeRepository extends JpaRepository<DeliveryTime, Integer> {
}