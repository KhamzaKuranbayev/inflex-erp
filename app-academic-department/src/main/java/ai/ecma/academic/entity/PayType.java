package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PayType extends AbstractEntity {
    private String name;//PAYTYPE ENUMGA O'TQAZILGAN BU DEYARLI ISHLATILMAYPTI
}
