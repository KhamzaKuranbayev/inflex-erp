package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

//USERGA BIRIKTI RILGAN ROLE LARNI SAQLASH UCHUN TABLE

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user_role")
public class UserRole extends AbsLongEntity {

    //USERGA BIRIKTIRILGAN ROLE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    private Role role;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    //AGAR USERGA BITTADAN KO'P ROLE BERILGAN BO'LSA, QAYSI ROLE ASOSIY EKANLIGINI BELGILASH UCHUN
    // (ASLIDA ROLENING ASOSIYLIGI BIZGA KERAK EMAS. BIZGA FAQAR SHU ASOSIY ROLE NING DEFAULT PAGE I KERAK BO'LADI)
    private Boolean main;

    public UserRole(Long roleId, UUID userId, Boolean main) {
        this.roleId = roleId;
        this.userId = userId;
        this.main = main;
    }
}
