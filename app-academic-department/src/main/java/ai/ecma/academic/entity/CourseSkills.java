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
 *SHU KURSDAN KEYIN NIMAGA ERISHADI?
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CourseSkills extends AbstractEntity {

    private Integer ord;//TARTIBI

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;//QAYSI KURS UCHUN

    @Column(nullable = false)
    private String text;//MAZMUNI
}
