package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * ASSISTENT YOKI MENTOR STUDENTNING SAVOLIGA FAQAT REPLY QILIB JAVOB BERSA
 * SHU ENTITYGA SAQLANADI.(CHAT, SAVOL JAVOB BO'LIMI)
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
public class  Answer extends AbstractEntity {
    /**
     * ASISTENT YOKI MENTOR TOMONIDAN YOZILGAN JAVOB(TEXT)
     */
    @Column(columnDefinition = "text", nullable = false)
    private String answer;
    /**
     * ASISTENT YOZGAN JAVOBNI OCHIRIB TASHLASHI MUMKIN, BU FIELD HABARNI OCHIRISH UCHUN KERAK
     */
    private Boolean deleted=false;
    /**
     * ASISTENTNING JAVOBI FAYL YOKI RASMLI BO'LSA ATTACHMENT KERAK BO'LADI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;
    /**
     * QAYSI SAVOLGA JAVOB BERILAYOTGANI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Support support;

    public Answer(String answer, Attachment attachment, Support support) {
        this.answer = answer;
        this.attachment = attachment;
        this.support = support;
    }
}
