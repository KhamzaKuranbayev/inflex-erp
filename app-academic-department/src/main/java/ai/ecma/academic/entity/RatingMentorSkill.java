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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * STUDENT NING KURS MENTORI MAHORATINI BAHOLASHI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"lesson_id", "created_by_id"}))
public class RatingMentorSkill extends AbstractEntity {
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Lesson lesson;// BAHO QAYSI LESSON GA TEGISHLI EKANLIGI

    @Column(nullable = false)
    private Integer rating;//QO'YILGAN BAHO

    @Column(columnDefinition = "text")
    private String description;//IZOH, MENTORNI BAHOLAYOTGANDA O'QUVCHI IZOH YOZISHI HAM MUMKIN (STUDENT TOMONIDAN QO'YILGAN BAHO 4 DAN PAST BO'LSA DESCRIPTION YOZISH MAJBURIY)

    private Boolean called=false;//MAS'UL XODIMLAR ASSISTANT YOKI MENTORNI PAST BAHOLAGAN STUDENTLAR DAN FEEDBACK OLISHI KERAK,STUDENTGA TELEFON QILIB

    private String supportDescription;//IZOH (MAVHUM HOZIRCHA)

//    public RatingMentorSkill(Lesson lesson, Integer rating, String description) {
//        this.lesson = lesson;
//        this.rating = rating;
//        this.description = description;
//    }
}
