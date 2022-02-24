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

/**
 * TELEGRAM GURUH VA UNING A'ZO LARI (STUDETLAR)
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TgGroupStudents extends AbstractEntity {

    /**
     * TELEGRAM GURUH A'ZOSI(STUDENT)
     */
    @Column(nullable = false)
    private UUID userId;

    /**
     * OQUVCHI QAYSI GURUHGA AZO
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;

}
