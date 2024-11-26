package vn.edu.hust.soict.japango.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.hust.soict.japango.common.enums.UserRole;

@Entity
@Table(name = "user_info")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String username;

    private String password;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;
}
