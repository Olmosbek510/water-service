package uz.inha.waterdeliveryProj.bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.inha.waterdeliveryProj.bot.entity.abs.AbsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class District extends AbsEntity {
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;
}
