package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.StudyType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * BOOTCAMPLARNING NARXLARI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bootcamp_id", "study_type"}))
public class BootcampCost extends AbstractEntity {

    /**
     * QAYSI BOOTCAMPNING NARXI EKANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Bootcamp bootcamp;

    /**
     * BOOTCAMP O'QISH TURI QANAQA EKANLIGI
     */
    @Column(nullable = false, name = "study_type")
    @Enumerated(EnumType.STRING)
    private StudyType studyType; //STUDY TYPE ENUMNI ICHIDA 2 XIL TURI BOR, 1-ONLINE,2-ONSITE

    /**
     * ASL NARXI
     */
    @Column(nullable = false)
    private Integer amount;

    /**
     * OYMA-OY TO'LASHDAGI NARXI
     */
    private Integer oneMonthAmount;

    /**
     * IKKIGA BO'LIB TO'LASA NARXI
      */
    private Integer twoStepAmount;
    /**
     * HAMMASINI BIRDANIGA TO'LASA NARXI
     */
    private Integer fullAmount;

    /**
     * SINOV OYI NARXI
     */
    private Integer tryMonth;

    /**
     * ACTIVE YOKI ACTIVE EMASLIGI
     */
    @Column(nullable = false)
    private Boolean active;
}
