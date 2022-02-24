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
 * BILDIRISHNOMALARNI TUR LARGA AJRATISH UCHUN
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationType extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String type;//BILDIRISHNOMALAR UCHUN TUR NOMI

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    private Boolean editable = true;//TAHRIRLASA BOLADIMI


}
