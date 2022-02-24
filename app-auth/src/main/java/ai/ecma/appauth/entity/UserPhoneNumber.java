package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsUUIDUserAuditEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

/**
 * FOYDALANUVCHINING TELEFON RAQAMLARI (BIR NECHTA TELEFON RAQAM BO'LISHI MUMKIN)
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "user_phone_number")
public class UserPhoneNumber extends AbsUUIDUserAuditEntity {
    // FOYDALANUVCHI TELEFON RAQAMLARI
    @Column(name = "phone_number")
    private String phoneNumber;

    //FOYDALANUCHI
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    //USHBU TELEFON RAQAM USER TOMONIDAN TASDIQLANGANMI?
    private Boolean accepted;

    private String comment;
}