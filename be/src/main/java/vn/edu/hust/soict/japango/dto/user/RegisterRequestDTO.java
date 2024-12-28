package vn.edu.hust.soict.japango.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    @NotEmpty(message = "ユーザー名を入力してください")
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "ユーザー名は文字、数字、またはアンダースコアのみを含むべきです")
    private String username;
    @NotEmpty(message = "パスワードを入力してください")
    @Size(min = 8, message = "パスワードは8文字以上である必要があります")
    private String password;
    @NotEmpty(message = "名前を入力してください")
    private String name;
    @NotEmpty(message = "メールアドレスを入力してください")
    @Email(message = "有効なメールアドレスを入力してください")
    private String email;
}
