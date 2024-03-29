package hometoogether.hometoogether.domain.notification;

import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private JwtProvider jwtProvider;
    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        User user = jwtProvider.getUserFromHeader();
        return notificationService.subscribe(user, lastEventId);
    }
}
