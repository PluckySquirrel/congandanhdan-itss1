package vn.edu.hust.soict.japango.dto.saved_result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.common.enums.Language;
import vn.edu.hust.soict.japango.common.enums.SaveType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedResultDTO {
    private String uuid;
    private String historyUuid;
    private ActionType action;
    private String input;
    private String output;
    private Language sourceLanguage;
    private Language targetLanguage;
    private LocalDateTime timestamp;
    private SaveType saveType;
}
