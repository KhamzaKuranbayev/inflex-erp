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
 * LESSON GA TEGISHLI BO'LGAN PREZENTATSIYALAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tutorial extends AbstractEntity {

    /**
     * TARTIBI
     */
    @Column(nullable = false)
    private Integer ord;

    /**
     * NOMI
     */
    @Column(nullable = false)
    private String title;
    /**
     * IZOH
     */
    @Column(columnDefinition = "text")
    private String description;

    /**
     * QAYSI LESSONGA TEGISHLILIGI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    /**
     * FAYL YOKI RASM BIRIKTIRILGAN BO'LSA
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

}
