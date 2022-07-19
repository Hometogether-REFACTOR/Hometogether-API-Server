package hometoogether.hometoogether.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HometoException extends RuntimeException {
    private final ErrorCode errorCode;
}
