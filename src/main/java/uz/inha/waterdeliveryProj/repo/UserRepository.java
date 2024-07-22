package uz.inha.waterdeliveryProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.inha.waterdeliveryProj.bot.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u where u.phone = :phone")
    Optional<User> findByUsername(String phone);
}