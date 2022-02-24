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
 * By: Akbarjon Ahmadjonov on 21/05/2021 at 02:55
 *
 * DARSLARDAGI "KO'CHIRIB OLISH UCHUN" BO'LIMI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Download extends AbstractEntity {

    @Column(nullable = false)
    private Integer ord; // yuklanmaning tartib raqami

    @Column(nullable = false)
    private String title; // yuklanmaning nomi

    @Column(columnDefinition = "text")
    private String description; // yuklanma tavsifi

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment; // yuklanadigan kontent

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Lesson lesson; // yuklanmaning qaysi darsga tegishli ekanligi
}
