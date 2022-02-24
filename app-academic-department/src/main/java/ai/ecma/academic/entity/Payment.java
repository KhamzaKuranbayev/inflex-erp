package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.EduTypeEnum;
import ai.ecma.academic.entity.enums.PayTypeEnum;
import ai.ecma.academic.entity.enums.PaymentState;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Ravshan on 20.05.2021.
 */

/**
 * PDP.UZ PLATFORMASIGA QILINGAN TO'LOVLAR
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends AbstractEntity {

    /**
    TO'LOV TURI (  PAYME,CLICK,YANDEX,CARD_TRANSFER,NAQD,CASHBACK)
     */
    @Enumerated(EnumType.STRING)
    private PayTypeEnum payType;

    private Integer amount;//TO`LOV MIQDORI

    private Integer leftover;//BALANSIDAGI PUL MIQDORI

    @Column(nullable = false)
    private UUID customerId;// STUDENTNING ID SI


//    @OneToOne(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private CustomerOrder order;//PAYME VA CLICK ORQALI TO`LOV QILINGANDA BERILADIGAN ORDER

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Vaucher vaucher;//FOYDALANUVCHI  VOUCHERI (AGAR BO`LSA)
//
//    @OneToOne(fetch = FetchType.LAZY)
//    private YandexPaymentOrder yandexPaymentOrder;//YANDEX ORQALI TO'LOV AMALGA OSHIRISH UCHUN BERILGAN ORDER

    private String description;//IZOH

    @Enumerated(EnumType.STRING)
    private PaymentState state;//TO'LOV XOLATI (MONEY_BACK,CREATED)

    @Enumerated(EnumType.STRING)
    private EduTypeEnum eduType;//TA'LIM TURI (ONLINE, OFFLINE, BOOTCAMP)

    @ManyToOne(fetch = FetchType.LAZY)
    private PaynetTransaction paynetTransaction;//PAYNET UCHUN TRANZAKSIYA

//    public Payment(PayTypeEnum payType, Integer amount, Integer leftover, User customer, CustomerOrder order,
//                   Vaucher vaucher, EduTypeEnum eduType) {
//        this.payType = payType;
//        this.amount = amount;
//        this.leftover = leftover;
//        this.customer = customer;
//        this.order = order;
//        this.vaucher = vaucher;
//        this.eduType = eduType;
//    }
}
