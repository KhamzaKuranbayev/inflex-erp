package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * VAUCHER  BERISH MAQSADI  (VOUCHER QAYERDA BERILDI: MFAKTOR UCHUN, DAVLETOV UCHUN, ZIYODULLOH UCHUN)
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Destination extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name; //VAUCHER MAQSADI UCHUN XABAR
}
