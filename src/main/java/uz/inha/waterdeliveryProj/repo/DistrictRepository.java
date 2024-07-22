package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.inha.waterdeliveryProj.bot.entity.District;

import java.util.List;
import java.util.UUID;

public interface DistrictRepository extends JpaRepository<District, UUID> {
    List<District> findDistrictsByRegionId(UUID regionId);

    @Query(nativeQuery = true, value = """
            select *
            from district
            where similarity(name, :districtName) > 0.2
            order by similarity(name, :districtName) desc limit 1
            """)
    District findBySimilarity(String districtName);
}