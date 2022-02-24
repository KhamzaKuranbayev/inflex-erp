package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * HAR BIR DARSNING BO'LIMLARI UCHUN STUDENTGA BERILADIGAN HASH LAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"part_id", "userId"}))
public class PartUserHash extends AbstractEntity {
    @Column(nullable = false)
    private String hash;//VIDEONI XAVFSILIGINI TA'MINLAYDIGAN SERVICE  HASH(CODE) BOOMSTRIM BERGAN HASHCODE

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Part part;//LESSONNING QAYSI VIDEOSI UCHUN BERILGANLIGI

    @Column(nullable = false)
    private UUID userId;//KIMGA BERILGANLIGI, YA'NI STUDENT NING ID SI
}
