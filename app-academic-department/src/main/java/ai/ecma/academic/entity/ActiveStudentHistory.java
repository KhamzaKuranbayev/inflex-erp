package ai.ecma.academic.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ai.ecma.academic.entity.template.AbstractEntity;

import javax.persistence.Entity;

/**
 * BY Abdusobur Xalimov 06.01.2021 11:02
 */

/**
 * active talablar tarixi uchun
 * har kuni GMT+05 bo'yicha yangilanadi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActiveStudentHistory extends AbstractEntity {
    /**
     * ACTIVE STUDENTLAR SONI
     */
    Integer count;

}
