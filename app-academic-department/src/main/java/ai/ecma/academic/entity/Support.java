package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.CoursePaymentFrom;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

/**
 * STUDENTNI MA'LUM BIR DARS BO'YICHA BERGAN SAVOLLAR SHU ENTITYGA YOZILADI
 * SAVOL-JAVOB BO'LIMIDA (VAZIFA EMAS)
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Where(clause = "deleted=false")
public class Support extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "text", length = 1000)
    private String question;//AGAR USERTOID FIELD NULL BO'LSA
    // STUDENT TOMONIDAN YOZILGAN XABAR.
    // AKS HOLDA MENTOR YOKI ASSISTENT
    // TOMONIDAN YOZILYAPTI

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Lesson lesson;//XABAR QAYSI DARSGA TEGISHLI EKANLIGI

    private Boolean isLooked = false;//MENTOR YOKI ASSISTENT TOMONIDAN O'QILGANMI

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;//FAYL

    private UUID userToId;//AYNAN QAYSI STUDENTGA YOZILYAPTI

    private Integer notificationId;//BOTDAGI MESSAGENI ID SI

    private Boolean deleted=false;//O'CHIRILGANMI


    @Enumerated(EnumType.STRING)
    private CoursePaymentFrom coursePaymentFrom;//QAYSI TA'LIM TURIDAN XABARNI YOZAYOTGANINI BILISH UCHUN

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;//KURS

//    public Support(String question, Lesson lesson, Boolean isLooked, Attachment attachment) {
//        this.question = question;
//        this.lesson = lesson;
//        this.isLooked = isLooked;
//        this.attachment = attachment;
//    }
//
//    public Support(String question, Lesson lesson, Boolean isLooked, Attachment attachment, UUID userToId, CoursePaymentFrom coursePaymentFrom, Course course) {
//        super();
//        this.question = question;
//        this.lesson = lesson;
//        this.isLooked = isLooked;
//        this.attachment = attachment;
//        this.userToId = userToId;
//        this.coursePaymentFrom = coursePaymentFrom;
//        this.course = course;
//    }
}
