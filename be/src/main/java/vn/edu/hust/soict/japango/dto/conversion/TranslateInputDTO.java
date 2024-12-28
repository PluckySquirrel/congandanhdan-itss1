package vn.edu.hust.soict.japango.dto.conversion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.soict.japango.common.enums.Language;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranslateInputDTO {
    @NotEmpty
    private String input;
    private Language sourceLanguage;
    @NotNull
    private Language targetLanguage;

    public Language getSourceLanguage() {
        if (sourceLanguage == null)
            return Language.JAPANESE;
        return sourceLanguage;
    }
}
