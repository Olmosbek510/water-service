package uz.inha.waterdeliveryProj.bot.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.District;
import uz.inha.waterdeliveryProj.bot.service.DistrictService;
import uz.inha.waterdeliveryProj.repo.DistrictRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;

    @Override
    public List<District> saveAll(List<District> districts) {
        return districtRepository.saveAll(districts);
    }

    @Override
    public List<District> findByRegionId(UUID regionId) {
        return districtRepository.findDistrictsByRegionId(regionId);
    }

    @Override
    public District findBySimilarity(String districtName) {
        return districtRepository.findBySimilarity(districtName);
    }

    @Override
    public District save(District district) {
        return districtRepository.save(district);
    }

    @Override
    public District findById(UUID districtId) {
        return districtRepository.findById(districtId).orElseThrow(() -> new RuntimeException("District not found"));
    }
}
