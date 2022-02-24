package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

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
@Entity(name = "question")
public class Question extends AbsLongEntity {

    // SAVOL NOMI
    @Column(name = "question",unique = true,nullable = false)
    private String question;
}