package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneAuth extends AbstractEntity {
    //BIR MARTA SMS BILAN SIGN IN QILGANDAN SO'NG,
    // TELEFON RAQAM SAQLAB QO'YILADI VA KEYINGI SAFAR SIGN IN QILGANDA SMS BILAN TEKSHIRISH SHART EMAS
    @Column(nullable = false)
    private String phoneNumber;
}
