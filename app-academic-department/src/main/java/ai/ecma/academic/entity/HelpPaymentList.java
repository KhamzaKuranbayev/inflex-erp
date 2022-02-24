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
 * SUPPORT KUNI SOTIB OLISH UCHUN QAYSI TO'LOVLARDAN QANCHA YECHILGANLIGI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpPaymentList extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Help help;// SUPPORT KUNLARI SOTIB OLINGAN TABLE
    // MISOL: TESHAVOY 10KUN SUPPORT SPRING KURSI 1-MODULI UCHUN SUPPORT OLDI

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Payment payment;// BALANSNI ANIQLASH UCHUN

    @Column(nullable = false)
    private Integer amount;// TO'LAGAN SUMMASI MISOL:10 KUN UCHUN 50000 UZS
}
