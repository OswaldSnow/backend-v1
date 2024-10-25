package backend.v1.configuration.exceptionHandle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    String message;
    int code;
}
