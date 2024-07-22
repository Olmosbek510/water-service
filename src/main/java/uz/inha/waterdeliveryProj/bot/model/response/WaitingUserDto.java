package uz.inha.waterdeliveryProj.bot.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaitingUserDto {
    private UUID id;
    private String phone;
    private LocalDateTime createdAt;

    public String getDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        return dateTimeFormatter.format(createdAt);
    }
}
