package vn.edu.hust.soict.japango.dto.saved_result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteSavedResultsResponseDTO {
    private Long numberDeleted;
}
