package vn.edu.hust.soict.japango.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequestDTO {
    @NotEmpty
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "should only contain letters, digits or underscores")
    private String username;
    @NotEmpty
    @Pattern(regexp = "[A-Za-z\\s]+", message = "should contain letters only")
    private String name;
    @NotEmpty
    @Email
    private String email;
}
