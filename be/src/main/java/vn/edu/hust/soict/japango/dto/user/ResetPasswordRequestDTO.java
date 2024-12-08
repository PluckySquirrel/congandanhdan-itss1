package vn.edu.hust.soict.japango.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequestDTO {
    @NotEmpty
    private String token;
    @NotEmpty
    @Size(min = 8, message = "should contain at least 8 characters")
    private String newPassword;
}
