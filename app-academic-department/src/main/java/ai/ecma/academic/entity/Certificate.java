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
 * PDP.UZ NING HOME PAGEDA 3TA TEMPLATE CERTIFICATE BOR SHU UCHUN KERAK BU
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificate extends AbstractEntity {

    /**
     * SERTIFIKAT NOMI
     */
    private String title;

    /**
     * QO'SHIMCHA MA'LUMOTI
     */
    private String description;

    /**
     * CERTIFICATE TURLARI
     */
//    @Column(nullable = false, unique = true)
//    private CertificateType certificateType;//3xil turi bor, 1-gold, 2-silver, 3-platinum

    /**
     * CERTIFICATE NING RASMI
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

}
