package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.inha.waterdeliveryProj.bot.entity.Region;

import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
    Optional<Region> findByName(String name);
}