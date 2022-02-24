package ai.ecma.academic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * By Yulchiyev Abbos 24.05.2021
 */

/**
 * SAYT DA TUZATISH ISHLATI KETYAPTI. {DEGAN XABAR CHIQARISH UCHUN}
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reconstruction {

    @Id
    private Integer id;
    /**
     * RECUNSTRUCTION PAGE KO'RINISHI YOKI KO'RINMASLIGI UCHUN!
     * (AGAR TRUE BO'LSA PAGE KO'RINADI)
     */
    private Boolean isActive;

    /**
     * FIREBASE ISHLAMAY QOLGAN PAYT BU FIELD TRU QILINSA
     * FIREBASEDAN FOYDALANMASDAN PULLIK SMS SERVICEDAN FOYDALANILADI
     */
    private Boolean dontUseFirebase;
}
