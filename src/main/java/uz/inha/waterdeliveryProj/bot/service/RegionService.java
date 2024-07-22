package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.Region;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegionService {
    void saveAll(List<Region> build);

    Optional<Region> findByName(String name);

    List<Region> findAll();

    Optional<Region> findById(UUID regionId);
}
