package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * COURSE PAYMENT UCHUN QAYSI PAYMENTDAN QANCHA TO'LANGANLIGI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePaymentList extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CoursePayment coursePayment;//QAYSI COURSE PAYMENT(KURS S0TIB OLINDANIDA SHU TABLEGA YOZILADI)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Payment payment;//QAYSI TO'LOV(BALANCNI TO'LDIRGANDA SHU PAYMENT TABLE YOZILADI)

    @Column(nullable = false)
    private Integer amount;//QANCHA YECHILDI SHU PAYMENTDAN(BALANCDAN QANCHA YECHILDI)
}
