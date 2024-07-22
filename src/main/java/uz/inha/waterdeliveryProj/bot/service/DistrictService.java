package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.District;

import java.util.List;
import java.util.UUID;

public interface DistrictService {
    List<District> saveAll(List<District> build);

    List<District> findByRegionId(UUID regionId);

    District findBySimilarity(String districtName);

    District save(District district);
}
