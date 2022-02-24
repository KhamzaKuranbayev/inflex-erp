package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.UserHistoryTypeEnum;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * user platformaga a'zo bolgandan boshlab hisobga olinadigan tarixi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserHistory extends AbstractEntity {

    /**
     * TARIX TURI, CHANGE_PHONE
     */
    @Enumerated(EnumType.STRING)
    private UserHistoryTypeEnum type;
    /**
     * IZOH
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

}
