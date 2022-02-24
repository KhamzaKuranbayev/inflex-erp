package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsUUIDNotUserAuditEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

//MIJOZLARGA REGISTER YOKI
// LOGIN QILGANDA RAQAMNI TASDIQLASH UCHUN SMS CODE YUBORILADI
//CODE SHU TABLEDA SAQLANADI
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE sms_code SET deleted=true WHERE id=?")
@Entity(name = "sms_code")
@Table(indexes = @Index(columnList = "phone_number"))
public class SmsCode extends AbsUUIDNotUserAuditEntity {
    //MIJOZNING TELEFONIGA YUBORILGAN TASDIQLASH KODI
    @Column(nullable = false)
    private String code;

    //QAYSI RAQAMGA BORDI SMS
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    //MIJOZ USHBU CODENI TEKSHIRIB BO'LDI, ISHLATIB BO'LINGAN KODMI?
    private Boolean checked = false;

    //USHBU MIJOZ UCHUN AVVAL YUBORGAN SMS LARI SONI LIMITDAN OSHGANDA,
    // ADMIN TOMONIDAN USHBU COLUMN TRUE QILINSA, HISOB-KITOB YANA 0 DAN BOSHLANADI
    private Boolean ignored = false;

    //USHBU COLUMN TRUE BO'LSA YANGI PASSWORD SET QILINMAYDI
    @Column(name = "checked_for_reset")
    private Boolean checkedForReset = false;

    public SmsCode(String code, String phoneNumber) {
        this.code = code;
        this.phoneNumber = phoneNumber;
    }
}
