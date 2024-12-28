package vn.edu.hust.soict.japango.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.common.enums.Language;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDTO {
    private String uuid;
    private ActionType action;
    private String input;
    private String output;
    private Language sourceLanguage;
    private Language targetLanguage;
    private LocalDateTime timestamp;
}
