package ai.ecma.academic.entity;


import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * KURS GA BOLGAN TALABLAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Requirement extends AbstractEntity {

    /**
     * TALAB NOMI, MASALAN:
     * css; html; javascript;
     */
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course; // KURS

}
