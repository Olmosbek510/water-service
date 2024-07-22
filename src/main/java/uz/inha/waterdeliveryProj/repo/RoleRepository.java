package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.inha.waterdeliveryProj.bot.entity.Role;
import uz.inha.waterdeliveryProj.bot.entity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where r.roleName = :roleName")
    Role findByName(RoleName roleName);
}