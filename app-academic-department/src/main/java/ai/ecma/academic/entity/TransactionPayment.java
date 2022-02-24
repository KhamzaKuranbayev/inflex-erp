package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.TransactionType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * BY Abdusobur Xalimov 11.01.2021 15:15
 */


/**
 * FINANCEGA PAYMENT YUBORILGANDA ERROR BO'LSA SHUNGA YOZILADI, KEYIN QAYTA YUBORADI
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionPayment extends AbstractEntity {

    /**
     * ERROR BO'LDIMI? / ERROR BO'LMADIMI?
     */
    @Column(nullable = false)
    private Boolean done = false;

    /**
     * TO'LOV
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    /**
     * TIMETABLE TO'LOVI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private TimeTablePayment timeTablePayment;

    /**
     * QAYSI TIMETABLEGA TEGISHLI TRANZAKSIYA
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private TimeTable timeTable;

    /**
     * MODULE TO'LOVI QAYTARILISHI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private CoursePartBack coursePartBack;

    /**
     * TO'LOV QAYTARILISHI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentBack paymentBack;

    /**
     * TRANZAKTSIYA TURLARI
     */
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public TransactionPayment(Boolean done, Payment payment, TransactionType transactionType) {
        this.done = done;
        this.payment = payment;
        this.transactionType = transactionType;
    }

    public TransactionPayment(Boolean done, TimeTable timeTable, TransactionType transactionType) {
        this.done = done;
        this.timeTable = timeTable;
        this.transactionType = transactionType;
    }

    public TransactionPayment(Boolean done, CoursePartBack coursePartBack, TransactionType transactionType) {
        this.done = done;
        this.coursePartBack = coursePartBack;
        this.transactionType = transactionType;
    }

    public TransactionPayment(Boolean done, TimeTablePayment timeTablePayment, TransactionType transactionType) {
        this.done = done;
        this.timeTablePayment = timeTablePayment;
        this.transactionType = transactionType;
    }

    public TransactionPayment(Boolean done, PaymentBack paymentBack, TransactionType transactionType) {
        this.done = done;
        this.paymentBack = paymentBack;
        this.transactionType = transactionType;
    }
}
