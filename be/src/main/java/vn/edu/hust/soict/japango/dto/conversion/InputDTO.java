package vn.edu.hust.soict.japango.dto.conversion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.soict.japango.exception.CustomExceptions;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputDTO {
    private String input;
    public void validate() {
        if (input == null || input.isEmpty()) {
            throw CustomExceptions.INPUT_IS_EMPTY_EXCEPTION;
        }
    }
}
