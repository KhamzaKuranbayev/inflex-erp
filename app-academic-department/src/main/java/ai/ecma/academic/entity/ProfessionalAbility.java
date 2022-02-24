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
 * PROFESSIONAL QOBILIYAT LAR
 * SHU ROADMAP DAN OLADIGAN FOYDALARI
 * YA'NI ROADMAP NI O'QIB QANDAY BILIMLARGA EGA BO'LISHI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProfessionalAbility extends AbstractEntity {

    @Column(nullable = false)
    private String text;//HAR BIR QOBILIYAT HAQIDA MA'LUMOT

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Roadmap roadmap;//QOBILIYAT  QAYSI  ROADMAP  GA  TEGISHLI  EKANLIGI

}
