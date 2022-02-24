package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsUUIDUserAuditEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

/**
 * FOYDALANUVCHILARGA ACCOUNTI
 * BO'YICHA YUZAGA KELISHI MUMKIN BO'LGAN MUAMMOLARNI
 * HAL QILISH UCHUN VA USERNI TASDIQLAB OLISH UCHUN BERILADIGAN SAVOLLAR
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user_question")
public class UserQuestion extends AbsUUIDUserAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    //QAYSI SAVOLNI TANLADI
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @ToString.Exclude
    private Question question;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    //MIJOZ TOMONIDAN SAVOLGA BERGAN JAVOBI
    @Column(name = "answer", nullable = false)
    private String answer;

    //MIJOZGA
    @Column(name = "order", nullable = false)
    private Integer order;
}