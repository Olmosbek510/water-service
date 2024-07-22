package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.inha.waterdeliveryProj.bot.entity.BottleType;

import java.util.List;

public interface BottleTypeRepository extends JpaRepository<BottleType, Integer> {
    List<BottleType> findAllByActiveTrue();
}