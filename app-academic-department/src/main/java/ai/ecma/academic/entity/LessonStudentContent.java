package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * O'QUVCHLARNI TIMETABLEGA YO'QLAMA QILISH
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"lesson_id", "studentId"}))
public class LessonStudentContent extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseLesson lesson;//QAYSI DARS

    @Column(nullable = false)
    private UUID studentId;//STUDENT

    private Boolean isParticipated;//KELDI-KELMADI

    private Boolean active;//STUDENT O'QIYAPTIMI YOKI YO'Q

    private UUID tutorId;//MENTOR
}
