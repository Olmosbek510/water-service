package uz.inha.waterdeliveryProj.bot.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.inha.waterdeliveryProj.bot.entity.*;
import uz.inha.waterdeliveryProj.bot.entity.enums.RoleName;
import uz.inha.waterdeliveryProj.bot.service.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final RegionService regionService;
    private final DistrictService districtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final BottleService bottleService;
    private final DeliveryTimeService deliveryTimeService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {
//        User user = User.builder()
//                .phone("+998933460021")
//                .password(passwordEncoder.encode("123"))
//                .roles(List.of(
//                        roleService.findByName(RoleName.ROLE_OPERATOR)
//                ))
//                .build();
//        userService.save(user);
        deliveryTimeService.saveAll(
                List.of(
                        DeliveryTime.builder()
                                .startTime(LocalTime.of(9, 0))
                                .endTime(LocalTime.of(12, 0))
                                .build(),
                        DeliveryTime.builder()
                                .startTime(LocalTime.of(13, 0))
                                .endTime(LocalTime.of(18, 0))
                                .build()
                )
        );
        if (ddl.equals("create")) {
            regionService.saveAll(List.of(
                    Region.builder()
                            .name("Toshkent")
                            .build(),
                    Region.builder()
                            .name("Sirdaryo")
                            .build(),
                    Region.builder()
                            .name("Jizzax")
                            .build(),
                    Region.builder()
                            .name("Samarqand")
                            .build(),
                    Region.builder()
                            .name("Qashqadaryo")
                            .build(),
                    Region.builder()
                            .name("Surxandaryo")
                            .build(),
                    Region.builder()
                            .name("Navoiy")
                            .build(),
                    Region.builder()
                            .name("Buxoro")
                            .build(),
                    Region.builder()
                            .name("Farg'ona")
                            .build(),
                    Region.builder()
                            .name("Namangan")
                            .build(),
                    Region.builder()
                            .name("Xorazm")
                            .build(),
                    Region.builder()
                            .name("Andijon")
                            .build()
            ));
            Optional<Region> toshkent = regionService.findByName("Toshkent");
            if (toshkent.isPresent()) {
                Region region = toshkent.get();
                districtService.saveAll(List.of(
                        District.builder()
                                .name("Yunusobod")
                                .region(region).build(),
                        District.builder()
                                .name("Olmazor")
                                .region(region).build(),
                        District.builder()
                                .name("Shayxontohur")
                                .region(region).build()
                ));
            }
        }
    }
}
