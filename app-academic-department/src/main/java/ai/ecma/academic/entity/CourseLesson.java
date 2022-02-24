package ai.ecma.academic.entity;


import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.sql.Timestamp;

/**
 * TIME TABLE UCHUN DARSLAR RO'YXATI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLesson extends AbstractEntity {
    @Column(nullable = false)
    private String name;//DARS MAVZUSI

    @Column(nullable = false)
    private Integer ord;//TARTIBI

    private Timestamp lessonDate;//QACHON O'TILGANI

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private TimeTable timeTable;//QAYSI TIME TABLE UCHUN
}
