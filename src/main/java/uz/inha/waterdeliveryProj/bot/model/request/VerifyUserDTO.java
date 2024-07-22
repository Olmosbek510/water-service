package uz.inha.waterdeliveryProj.bot.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyUserDTO {
    private UUID tgUserId;
    private Float longitude;
    private Float latitude;
    private UUID districtId;
    private String addressLine;
}
