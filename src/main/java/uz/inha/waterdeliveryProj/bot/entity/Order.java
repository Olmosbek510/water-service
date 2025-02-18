package uz.inha.waterdeliveryProj.bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import uz.inha.waterdeliveryProj.bot.entity.enums.OrderStatus;
import uz.inha.waterdeliveryProj.bot.model.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private TelegramUser telegramUser;
    @CreationTimestamp
    private LocalDate day;
    @ManyToOne
    private DeliveryTime deliveryTime;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private Integer bottleCount;
    @Embedded
    private Location location;
    @ManyToOne
    private BottleType bottleType;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
