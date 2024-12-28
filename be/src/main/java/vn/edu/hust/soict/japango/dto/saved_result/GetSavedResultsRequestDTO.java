package vn.edu.hust.soict.japango.dto.saved_result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetSavedResultsRequestDTO {
    private String keyword;
    private int page;
    private int size;

    public String getKeyword() {
        if (keyword == null) return "";
        return keyword;
    }

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
