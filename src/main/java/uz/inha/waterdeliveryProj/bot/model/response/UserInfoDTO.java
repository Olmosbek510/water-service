package uz.inha.waterdeliveryProj.bot.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.inha.waterdeliveryProj.bot.entity.Region;
import uz.inha.waterdeliveryProj.bot.model.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {
    private Location location;
    private String phone;
    private Region region;
}
