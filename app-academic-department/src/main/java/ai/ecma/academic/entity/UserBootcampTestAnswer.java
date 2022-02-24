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
 * BOOTCAMPLAR UCHUN TEST JAVOBLARI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBootcampTestAnswer extends AbstractEntity {

    /**
     * SAVOL NING JAVOBI (TALABA TOMONIDAN)
     */
    @Column(nullable = false)
    private String answer;

    /**
     * QAYSI SAVOL EKANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserBootcampTest test;
    /**
     * JAVOBNING TO'G'RI YO NOTO'G'RI LIGI
     */
    private Boolean correct;
}
