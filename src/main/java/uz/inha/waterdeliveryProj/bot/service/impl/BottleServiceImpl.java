package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.BottleType;
import uz.inha.waterdeliveryProj.bot.service.BottleService;
import uz.inha.waterdeliveryProj.repo.BottleTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BottleServiceImpl implements BottleService {
    private final BottleTypeRepository bottleTypeRepository;

    @Override
    public BottleType save(BottleType bottleType) {
        return bottleTypeRepository.save(bottleType);
    }

    @Override
    public List<BottleType> saveAll(List<BottleType> bottleTypes) {
        return bottleTypeRepository.saveAll(bottleTypes);
    }

    @Override
    public List<BottleType> findAll() {
        return bottleTypeRepository.findAll();
    }

    @Override
    public List<BottleType> findActives() {
        return bottleTypeRepository.findAllByActiveTrue();
    }

    @Override
    public BottleType findById(int bottleId) {
        return bottleTypeRepository.findById(bottleId).orElseThrow(() -> new RuntimeException("Bottle type not found"));
    }


}
