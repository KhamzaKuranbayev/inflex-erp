package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * BOOTCAMPLAR UCHUN BERILADIGAN TEST
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBootcampTest extends AbstractEntity {
    /**
     * TEST SAVOLI
     */
    @Column(nullable = false)
    private String question;
    /**
     * TARTIBI
     */
    @Column(nullable = false)
    private Integer ord;
}
