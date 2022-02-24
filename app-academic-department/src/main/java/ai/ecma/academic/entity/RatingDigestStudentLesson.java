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
import java.util.UUID;

/**
 *
 *    STUDENT DARSNI NECHA FOIZ BAHO BILAN O'ZLASHTIRGANLIGI UCHUN
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lesson_id"}))
public class RatingDigestStudentLesson extends AbstractEntity {

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Column(nullable = false)
    private UUID studentId;// BAHO QAYSI STUDENT GA QO'YILAYOTGANLIGI

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Lesson lesson;// BAHO QAYSI DARS UCHUN QO'YILAYOTGANLIGI

    @Column(nullable = false)
    private Integer rating;//STUDENT UCHUN QO'YILGAN RATING YA'NI BALL

    @Column(columnDefinition = "text")
    private String description;//IZOH
}
