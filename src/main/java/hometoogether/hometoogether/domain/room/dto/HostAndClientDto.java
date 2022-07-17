package hometoogether.hometoogether.domain.room.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HostAndClientDto {
    private String hostUser;
    private String clientUser;
}
