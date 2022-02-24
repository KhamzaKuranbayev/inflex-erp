package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsUUIDUserAuditEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * FOYDALANUVCHI QAYSI QURILMALAR ORQALI PLATFORMAGA KIRGANLIGI HAQIDA TARIX
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
@Entity(name = "user_device")
public class UserDevice extends AbsUUIDUserAuditEntity {
    // FOYDALANUVCHI QURILMASINING NOMI (WIN: X64, iPhone; CPU iPhone O , VA BOSHQALAR )
    @Column(name = "user_agent")
    private String userAgent;

    //FOYDALANUCHI
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private User user;

}