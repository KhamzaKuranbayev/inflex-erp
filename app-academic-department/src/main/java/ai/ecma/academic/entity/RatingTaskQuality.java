package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * By Yulchiyev Abbos 24.05.2021
 */

/**
 * STUDENT NING VAZIFA SIFATI UCHUN QO'YGAN BAHOSI
 * DARS VAZIFALARI BAJARILGANDAN KEYIN BAHOLANUVCHI QISIM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"lesson_id", "createdById"}))
public class RatingTaskQuality extends AbstractEntity {

    /**
     * BAHOLANAYOTGAN DARS
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    /**
     * SUDENTNI TASK SIFATIGA QO'YGAN BAHOSI
     */
    @Column(nullable = false)
    private Integer rating;

    /**
     * STUDENT TOMONIDAN VAZIFA UCHUN YOZILADIGAN IZOH
     * (STUDENT TOMONIDAN QO'YILGAN BAHO 4 DAN PAST BO'LSA DESCRIPTION YOZISH MAJBURIY)
     */
    @Column(columnDefinition = "text")
    private String description;

    /**
     * ASSISTANT YOKI MENTOR PAST BAHOLAGAN STUDENTLAR DAN FEEDBACK OLISHI KERAK,
     * TELEFON ORQALI
     */
    private Boolean called = false;

    /**
     * MENTORNING STUDENT VAZIFALARI UCHUN YOZGAN IZOHI(IN OLD VERSION)
     */
    private String supportDescription;

    public RatingTaskQuality(Lesson lesson, Integer rating, String description) {
        this.lesson = lesson;
        this.rating = rating;
        this.description = description;
    }
}
