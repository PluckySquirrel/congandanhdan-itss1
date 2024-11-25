package vn.edu.hust.soict.japango.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.hust.soict.japango.dto.common.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    ResponseEntity<ErrorDTO> handleApiException(ApiException exception) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .status(exception.getStatus().value())
                .errorCode(exception.getErrorCode())
                .message(exception.getMessage().formatted(exception.getArgs()))
                .build();
        return new ResponseEntity<>(errorDTO, exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDTO> handleGlobalException(Exception exception) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
