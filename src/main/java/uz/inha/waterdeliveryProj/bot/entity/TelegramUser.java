package uz.inha.waterdeliveryProj.bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.inha.waterdeliveryProj.bot.entity.abs.AbsEntity;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.model.Location;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TelegramUser extends AbsEntity {
    private Long chatId;
    @Enumerated(EnumType.STRING)
    private TelegramState state = TelegramState.START;
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private District district;
    private Integer deletingMessage;
    @Embedded
    private Location location;
    private boolean verified = false;
    private String addressLine;
    private Integer orderCount = 1;
    public boolean checkState(TelegramState telegramState) {
        return this.state.equals(telegramState);
    }
    private Integer bottleCount = 2;
    @ManyToOne(fetch = FetchType.EAGER)
    private BottleType bottleType;
    private Integer editingMessageId;
}
