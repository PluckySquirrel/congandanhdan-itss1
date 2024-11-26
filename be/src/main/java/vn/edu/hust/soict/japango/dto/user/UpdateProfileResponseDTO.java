package vn.edu.hust.soict.japango.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileResponseDTO {
    private String uuid;
    private String username;
    private String name;
    private String email;
    private LocalDateTime updatedAt;
}
