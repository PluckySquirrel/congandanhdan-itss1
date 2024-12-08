package vn.edu.hust.soict.japango.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.hust.soict.japango.common.enums.TokenType;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private String value;

    private LocalDateTime expireTime;

    @Builder.Default
    private Boolean isUsed = Boolean.FALSE;
}
