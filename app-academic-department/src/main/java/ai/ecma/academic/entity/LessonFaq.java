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

/**
 * DARSLAR BO'YICHA KO'P BERILADIGAN SAVOLLAR.
 * STUDENTGA YUBORISH UCHUN TAYYOR SAVOL SHABLONLARI (MENTOR YOKI ASSISTANT TOMONIDAN)
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonFaq extends AbstractEntity {

    private String title; // SAVOL TITLE I

    @Column(columnDefinition = "text")
    private String text; // SAVOL NING BODY SI

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;// SAVOL YUZASIDAN YUBORILGAN FAYL

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;// USHBU SAVOL QAYSI DARS BO'YICHA BERILGANLIGI
}
