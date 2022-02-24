package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * TELEFONGA YUBORILGAN SMS NI FIREBASE DAN TASHQARIDA TEKSHIRISH  ?? TUWUNIWIM KK!
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCode extends AbstractEntity {
    @Column(nullable = false)
    private String code;// Foydalanuvchiga  YUBORILGAN CODE
    @Column(nullable = false)
    private String phoneNumber;//   KOD YUBORILGAN TELEFON RAQAM
    private Boolean checked = false;//YUBORILGAN KOD KIRITILGANLIGINI TEKSHIRADI .
}
