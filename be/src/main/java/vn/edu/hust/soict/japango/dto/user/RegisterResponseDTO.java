package vn.edu.hust.soict.japango.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.soict.japango.common.enums.UserRole;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDTO {
    private String uuid;
    private String username;
    private String name;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
}
