package uz.inha.waterdeliveryProj.bot.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.inha.waterdeliveryProj.bot.entity.*;
import uz.inha.waterdeliveryProj.bot.entity.enums.OrderStatus;
import uz.inha.waterdeliveryProj.bot.entity.enums.RoleName;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.service.*;
import uz.inha.waterdeliveryProj.bot.utils.DistanceUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final OrderService orderService;
    private final DistanceUtil distanceUtil;
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
//        userService.save(user)
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

//    private void genRandomOrders(){
//        orderService.saveAll(List.of(
//                Order.builder()
//                        .id(1)
//                        .telegramUser(TelegramUser.builder()
//                                .district(districtService.findById(UUID.fromString("9ba180ac-2302-468a-8a3d-c6055ccf11a1")))
//                                .build())
//                        .day(LocalDate.now())
//                        .orderStatus(OrderStatus.CREATED)
//                        .deliveryTime(deliveryTimeService.findById(1))
//                        .bottleCount(0)
//                        .bottleType(bottleService.findById(1))
//                        .location(new Location(41.326066256161816f, 69.21278326762867f))
//                        .build(),
//                Order.builder()
//                        .id(2)
//                        .telegramUser(TelegramUser.builder()
//                                .district(districtService.findById(UUID.fromString("9ba180ac-2302-468a-8a3d-c6055ccf11a1")))
//                                .build())
//                        .day(LocalDate.now())
//                        .orderStatus(OrderStatus.CREATED)
//                        .deliveryTime(deliveryTimeService.findById(1))
//                        .bottleCount(0)
//                        .bottleType(bottleService.findById(1))
//                        .location(new Location(41.32205578006318f, 69.21790052226007f))
//                        .build()
//        ));
//    }
}
