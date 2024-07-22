package uz.inha.waterdeliveryProj.bot.entity;

import jakarta.persistence.Entity;
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
public class Region extends AbsEntity {
    private String name;
}
