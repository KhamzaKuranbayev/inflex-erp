package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

/**
 * ASSISTENT YOKI MENTOR STUDENTNING SAVOLIGA FAQAT REPLY QILIB JAVOB BERSA
 * SHU ENTITYGA SAQLANADI.(CHAT, SAVOL JAVOB BO'LIMI)
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ketmon extends AbstractEntity {
    private String name;
}
