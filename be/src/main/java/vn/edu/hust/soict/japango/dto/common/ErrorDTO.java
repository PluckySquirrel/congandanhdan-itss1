package vn.edu.hust.soict.japango.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
    @Builder.Default
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private Integer status;
    private Integer errorCode;
    private String message;
}
