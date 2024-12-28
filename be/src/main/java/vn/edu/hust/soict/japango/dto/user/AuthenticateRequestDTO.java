package vn.edu.hust.soict.japango.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticateRequestDTO {
    @NotEmpty(message = "ユーザー名を入力してください")
    private String username;
    @NotEmpty(message = "パスワードを入力してください")
    private String password;
}
