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
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCustomField extends AbstractEntity {
    @Column(nullable = false)
    private String fieldName;//FIELD NOMI
    @Column(nullable = false)
    private String fieldValue;//FIELD NING VALUE SI
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Column(nullable = false)
    private UUID userId;//KIMGA EKANLIGI YA'NI STUDENT
}