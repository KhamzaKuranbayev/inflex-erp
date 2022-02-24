package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.TaskStatus;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * MENTOR YOKI ASSISTENTNING STUDENT JO'NATGAN  JAVOBGA YOZGAN JAVOBI, YOKI BAHOLAGAN BAHOSI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class UserTaskAnswer extends AbstractEntity {
    /**
     * STUDENTGA UYGA VAZIFASI UCHUN BERILGAN JAVOB
     */
    @Column(columnDefinition = "text")
    private String answer;

    /**
     * JAVOB FAYL YOKI RASM BILAN JO'NATILGAN BO'LISHI MUMKIN
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    /**
     * STUDNENT JO'NATGAN JAVOB
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserTask userTask;

    /**
     * VAZIFANING STATUSI (CREATE,CANCEL,COMPLETE)
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    /**
     * STUDENTGA JAVOBI UCHUN QO'YILGAN BAHO
     */
    private Integer rating;

    /**
     * STUDENT TOMONIDAN BERILGAN JAVOB SAVOL-JAVOB BO'LIMIDANMI ?
     */
    private Boolean isComment;

    /**
     * MENTOR TOMONIDAN YOZILGAN XABAR O'CHIRILGANMI?
     * (ADASHIB XABAR JO'NATSA DELETE QILISH UN)
     */
    private Boolean deleted=false;

    public UserTaskAnswer(String answer, Attachment attachment, UserTask userTask, TaskStatus taskStatus, Integer rating) {
        super();
        this.answer = answer;
        this.attachment = attachment;
        this.userTask = userTask;
        this.taskStatus = taskStatus;
        this.rating = rating;
    }
}
