package hometoogether.hometoogether.domain.room.dto;

import hometoogether.hometoogether.domain.room.domain.WebSocketMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class WebSocketMessageDto {

    private String from;
    private String type;
    private String data;
    private Object candidate;
    private Object sdp;

    public WebSocketMessage toEntity(){
        WebSocketMessage build = WebSocketMessage.builder()
                .from(from)
                .type(type)
                .data(data)
                .candidate(candidate)
                .sdp(sdp)
                .build();

        return build;
    }
}
