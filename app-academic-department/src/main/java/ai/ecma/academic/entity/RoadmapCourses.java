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
 * ROADMAP KURSLARI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoadmapCourses extends AbstractEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;//ROADMAP UCHUN KURS

    private Integer ord;//TARTIB RAQAM

    private Integer ordBootcamp;//BOOTCAMP UCHUN TARTIB RAQAM

}
