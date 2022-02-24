package ai.ecma.academic.entity;



import ai.ecma.academic.entity.enums.StudyType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * MUTAXASSISLIK (ROADMAP) UCHUN QILINGAN TO'LOV
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoadmapPayment extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Roadmap roadmap;//MUTAXXASISLIK

    @Enumerated(EnumType.STRING)
    private StudyType studyType;//TA'LIM TURI

    @Column(nullable = false)
    private UUID userId;//KIM TO'LOV QILGANLIGI (SOTIB OLGANLIGI)

    @Column(nullable = false)
    private Boolean isFinished;//ROADMAP TUGATILGANMI

    private Timestamp finishedTime;//ROADMAP KURSINI TUGATGAN VAQTI

}
