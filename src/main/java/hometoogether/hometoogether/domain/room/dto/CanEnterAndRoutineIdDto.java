package hometoogether.hometoogether.domain.room.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CanEnterAndRoutineIdDto {

    private boolean canEnter;
    private Long routineId;
}
