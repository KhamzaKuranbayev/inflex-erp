package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;

/**
 * ATTACHMENTLARNIGN YA'NI FAYLLARNING CONTENTI SHU TABLEGA SAQLANADI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent extends DateAudit {
    /**
     * UUID BILAN GENERATSIYA QILINADIGAN ID
     */
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    /**
     * FAYLLARING CONTENTI
     */
    private byte[] content;

    /**
     * CONTENTGA ATTACHMENT ULANGAN, BITTA ATTACHMENTNING BITTA CONTENTI BO'LADI
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;


    public AttachmentContent(byte[] content, Attachment attachment) {
        this.content = content;
        this.attachment = attachment;
    }
}
