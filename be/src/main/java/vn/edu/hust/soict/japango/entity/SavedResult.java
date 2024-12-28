package vn.edu.hust.soict.japango.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.common.enums.Language;

import java.util.UUID;

@Entity
@Table(name = "saved_result")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private Long userId;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    @Column(name = "input", length = 5000)
    private String input;

    @Column(name = "output", length = 5000)
    private String output;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Language sourceLanguage = Language.JAPANESE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Language targetLanguage = Language.JAPANESE;
}
