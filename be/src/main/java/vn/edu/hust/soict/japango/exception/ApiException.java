package vn.edu.hust.soict.japango.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final Integer errorCode;
    private Object[] args;
    private final HttpStatus status;

    public ApiException(Integer errorCode, String message) {
        this(errorCode, message, HttpStatus.BAD_REQUEST);
    }

    public ApiException(Integer errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
    
    public ApiException setArgs(Object... args) {
        this.args = args;
        return this;
    }
}
