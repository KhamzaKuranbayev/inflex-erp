package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.PayTypeEnum;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * STUDENT GA PUL QAYTARISH
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentBack extends AbstractEntity {
    @Column(nullable = false)
    private UUID userId;// KIMGA PUL QAYTARILGANLIGI

    @Column(nullable = false)
    private Integer amount;//QAYTARILGAN PUL MIQDORI

    @Enumerated(EnumType.STRING)
    private PayTypeEnum payType; //TO'LOV TURI (MASALAN: PAYME,CLICK,YANDEX,CARD_TRANSFER,NAQD,CASHBACK)


    @Column(nullable = false)
    private Timestamp backTime;//TO`LOV QAYTARILGAN VAQTI

    //FINANCDAGI KASSANING IDSI, YANGI FORMATDA BU ISHLATILMASLIGI MUMKIN
    private Integer cashBoxId;

    //CLICKLARDAYAM PUL O'TKAZMALARI AMALGA OSHIRILAYOTGANDA KOMISSIYA FOIZI OLIB QOLINADI, SHUNGA O'XSHAB BU YERDA HAM KOMISSIYA FOIZI TO'LOVDAN OLIB QOLINADI
    //LEKIN BU HAM YANGI VERSIYADA ISHLATILMASLIGI MUMKIN
    private Double commissionFee;//

}
