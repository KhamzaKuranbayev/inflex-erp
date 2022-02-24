package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * MENTORLARNING KOPMANIYALAR UCHUN
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company extends AbstractEntity {

    /**
     * COMPANIYA NOMI
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * COMPANIYA UCHUN RASM YOKI COMPANIYANING RASMI
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Attachment attachment;
}
