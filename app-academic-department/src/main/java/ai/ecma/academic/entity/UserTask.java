package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.CoursePaymentFrom;
import ai.ecma.academic.entity.enums.TaskStatus;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Created by Sirojov on 20.02.2019.
 */

/**
 * TALABA TOMONIDAN VAZIFAGA YUBORILGAN JAVOB
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Where(clause = "deleted=false")
public class UserTask extends AbstractEntity {

    /**
     * XABAR FAYL YOKI RASMLI BO'LISHI MUMKIN
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    /**
     * XABAR MATNI
     */
    @Column(columnDefinition = "text", length = 2000) // 1000 TA EDI 2000 TA QILDIK
    private String text;

    /**
     * STUDENT YUBORGAN TASK NING XOLATI
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.CREATE;

    /**
     * QAYSI TASKGA JAVOB YUBORGANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    /**
     * O'QILGAN ,O'QILMAGAN (MENTOR YOKI .. UCHUN)
     */
    private Boolean isLooked = false;

    /**
     * TASKGA QO'YILGAN FOIZ
     */
    private Integer percent;

    /**
     * XABARR YUBORISH UCHUN ID(TELEGRAM BOT UCHUN)
     */
    private Integer notificationId;

    /**
     * COURSE NI QAYSI QISMDAN SOTIB OLGANLIGI( ROADMAP,
     *     BOOTCAMP,
     *     COURSE,
     *     TRY_LESSON,)
     */
    @Enumerated(EnumType.STRING)
    private CoursePaymentFrom coursePaymentFrom;

    /**
     * QAYSI KURSDAN VAZIFA YUBORGANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    public UserTask(Attachment attachment, String text, TaskStatus taskStatus, Task task, Boolean isLooked, Integer percent, CoursePaymentFrom coursePaymentFrom, Course course) {
        this.attachment = attachment;
        this.text = text;
        this.taskStatus = taskStatus;
        this.task = task;
        this.isLooked = isLooked;
        this.percent = percent;
        this.coursePaymentFrom = coursePaymentFrom;
        this.course = course;
    }
}
