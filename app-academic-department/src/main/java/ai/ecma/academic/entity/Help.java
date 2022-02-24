package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * MODULGA START BOSILGANDA BERILADIGAN SUPPORT KUNI SAQLANADIGAN TABLE
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Help extends AbstractEntity {

    /**
     * QAYSI KURSNI SOTIB OLGANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private CoursePayment coursePayment;

    /**
     * 45 KUNLIK SUPPPORTNI QANCHAGA SOTIB OLGANI
     */
    private Double amount;

    /**
     * SUPPORTDAN MENTORGA BERILADIGAN FOIZI
     */
    private Integer salaryPercent;

    /**
     * BERILADIGAN SUPPORT VAQTI
     */
    private Integer day;

    /**
     * SUPPORT ACTIVE MI (SUPPORT VAQTI TUGAGAN YOKI BORLIGINI BILISH UCHUN)
     */
    private Boolean active;

    /**
     * SUPPORT USTIGA SUPPORT SOTIB OLGANADA HISTORY CHIQARISH UCHUN
     */
    private Integer oldDay;

    public Help(CoursePayment coursePayment, Double amount, Integer salaryPercent, Integer day, Boolean active) {
        this.coursePayment = coursePayment;
        this.amount = amount;
        this.salaryPercent = salaryPercent;
        this.day = day;
        this.active = active;
    }
}
