package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.service.RegionService;
import uz.inha.waterdeliveryProj.repo.RegionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public void saveAll(List<Region> regions) {
        regionRepository.saveAll(regions);
    }

    @Override
    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public Optional<Region> findById(UUID regionId) {
        return regionRepository.findById(regionId);
    }
}
