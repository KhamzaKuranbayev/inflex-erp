package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * By Yulchiyev Abbos 24.05.2021
 */

/**
 * STUDENTNING VAZIFASI UCHUN QO'YILAYOTGAN BAHO(VAZIFA TEKSHIRUVCHI TOMONIDAN QO'YILADI)
 * DARS VAZIFALARI BAJARILGANDAN KEYIN BAHOLANUVCHI QISIM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "lesson_id"}))
public class RatingTaskQualityStudentLesson extends AbstractEntity {

    /**
     * BAHOLANAYOTGAN STUDNETNING ID si
     */
    @Column(nullable = false)
    private UUID studentId;

    /**
     * QAYSI DARS UCHUN QO'YILGANI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    /**
     * QO'YILGAN RATING(BAHO, VAZIFA TEKSHIRUVCHI TOMONIDAN QO'YILADI)
     */
    @Column(nullable = false)
    private Integer rating;

    /**
     * VAZIFA TEKSHIRAYOTGAN MENTOR YOKI ASSISTENT TOMONIDAN
     * STUDENTNING BAJARGAN VAZIFAZIYA YOZGAN IZOHI
     */
    @Column(columnDefinition = "text")
    private String description;
}
