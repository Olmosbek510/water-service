package uz.inha.waterdeliveryProj.bot.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.inha.waterdeliveryProj.bot.entity.District;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.entity.Role;
import uz.inha.waterdeliveryProj.bot.entity.User;
import uz.inha.waterdeliveryProj.bot.entity.enums.RoleName;
import uz.inha.waterdeliveryProj.bot.service.DistrictService;
import uz.inha.waterdeliveryProj.bot.service.RegionService;
import uz.inha.waterdeliveryProj.bot.service.RoleService;
import uz.inha.waterdeliveryProj.bot.service.UserService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final RegionService regionService;
    private final DistrictService districtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
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
