package vn.edu.hust.soict.japango.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.edu.hust.soict.japango.common.enums.ActionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHistoryRequest {
    private ActionType actionType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int page;
    private int size;

    public LocalDateTime getFromDateTime() {
        if (fromDate == null) return LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 0);
        return fromDate.atStartOfDay();
    }

    public LocalDateTime getToDateTime() {
        if (toDate == null) return LocalDate.now().plusDays(1).atStartOfDay();
        return toDate.plusDays(1).atStartOfDay();
    }

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
