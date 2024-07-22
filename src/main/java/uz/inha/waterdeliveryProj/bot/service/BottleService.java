package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.BottleType;

import java.util.List;

public interface BottleService {
    BottleType save(BottleType bottleType);

    List<BottleType> saveAll(List<BottleType> bottleTypes);

    List<BottleType> findAll();

    List<BottleType> findActives();

    BottleType findById(int bottleId);
}
