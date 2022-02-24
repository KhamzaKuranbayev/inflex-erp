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
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * MODULENI PULINI STUDENTGA QAYTARISH
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePartBack extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CoursePart coursePart;//QAYSI MODULEni QAYTARIB OLGAN

    @Column(nullable = false)
    private UUID studentId;//QAYSI STUDENTGA PUL QAYTARILGAN

    private Timestamp backTime;//PUL STUDENTGA QAYTARILGAN VAQT

    @Column(nullable = false)
    @Min(0)
    private Double backAmount;//QANCHA PUL QAYTARILDI

    private Double buyedAmount;//SOTIB OLGAN SUMMASI

    private Timestamp startTime;//QACHON KURS BOSHLAGAN
@Column(nullable = false)
    private UUID mentorId;//MODUL QAYSI MENTORGA TEGISHLI

    private Double cancelledMentorSalarySum;//MENTORDAN QANCHA QAYTARILADI

}
