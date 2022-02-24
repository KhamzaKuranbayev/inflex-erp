package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.MonthStartReportType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.UUID;

/**
 * HAR OY BERILADIGAN REPORTLAR UCHUN
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MonthStart extends AbstractEntity {

    private Integer data;//NECHTA STUDENT ACTIVE LIGI,NECHTA STUDENT STOP BOLGANLIGI, MonthStartReportType GA NISBATAN

    private UUID courseId;//ANIQ BIR KURS BO'YICHA REPORTLAR BERILADI

    @Enumerated(EnumType.STRING)
    private MonthStartReportType type;//REPORT QAYSI TURGA MANSUB EKANLIGI

    private Date date;//QAYSI OY UCHUN EKANLIGI,OYNING BIRINCHI KUNI YOZILADI ASOSAN

}
