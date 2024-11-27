package vn.edu.hust.soict.japango.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object value) {
        super(
                1404,
                "%s not found with %s: %s".formatted(resourceName, fieldName, value.toString()),
                HttpStatus.NOT_FOUND
        );
    }
}
