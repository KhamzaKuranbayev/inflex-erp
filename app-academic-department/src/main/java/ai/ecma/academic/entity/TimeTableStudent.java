package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * TIME_TABLE NING STUDENTLARI
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "time_table_id"}))
public class TimeTableStudent extends AbstractEntity {
    /**
     * QAYSI TIME TABLE EKANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TimeTable timeTable;

    /**
     * TIME TABLEGA QOSHILGAN STUDENTLAR
     */
    @Column(nullable = false)
    private UUID studentId;

    //student shu timetable uchun to'laydigan puli
    private Integer paySum;

    /**
     * ACTIVE YOKI ACTIVE EMASLIGI/O'QIYABDIMI YO'QMI?
     */
    private Boolean active;

    /**
     * PUL O'TKAZILGANMI?
     */
    private Boolean transfered;
}
