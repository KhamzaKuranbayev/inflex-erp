package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import ai.ecma.academic.entity.enums.*;
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
 * STUDENT TIME_TABLE UCHUN QILGAN TO'LOVI
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TimeTablePayment extends AbstractEntity {

    /**
    TIME_TABLE NING O'QUVCHISI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TimeTableStudent timeTableStudent;

    //student shu timetable uchun to'lagan puli
    @Column(nullable = false)
    private Integer paySum;

    //to'lagan qaytargan darsi nomeri ///nullableni ochib qo'yish kerak
//    @Column(nullable = false)
    private Integer lessonOrd = 0;

    /**
     * TO'LOV TURI, TO'LOVMI YOKI PUL QAYTARISH. BACK, PAY
     */
    @Enumerated(EnumType.STRING)
    private TimeTablePaymentStateEnum state;

    /**
     * TAFSILOTLARI
     */
    @Column(columnDefinition = "text")
    private String details;

    /**
    TIME_TABLE NING BOSHLANGAN YO BOSHLANMAGANLIGI
     */
    private Boolean started;

    public TimeTablePayment(TimeTableStudent timeTableStudent, Integer paySum, TimeTablePaymentStateEnum state, String details, Boolean started) {
        this.timeTableStudent = timeTableStudent;
        this.paySum = paySum;
        this.state = state;
        this.details = details;
        this.started = started;
    }
}
