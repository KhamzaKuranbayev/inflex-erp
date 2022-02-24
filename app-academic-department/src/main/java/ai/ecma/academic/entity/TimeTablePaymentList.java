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
 * TIME_TABLE GA QILINGAN TO'LOV LAR JAMLAMMASI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTablePaymentList extends AbstractEntity {
    /**
     * QAYSI TIME TABLEDA TO'LOV QILAYOTGANI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TimeTable timeTable;

    /**
     * TO'LOV
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Payment payment;

    /**
     * SUMMA
     */
    @Column(nullable = false)
    private Integer amount;
}
