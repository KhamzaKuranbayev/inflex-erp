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
 * CREATED BY JAMSHID VAHOBOV 20.05.2021
 * KURSNINNG MODULELARI (ONLINE, ONSITE UCHUN)
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePart extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;//QAYSI KURS UCHUN

    private Integer ord;//TARTIBI

    private Double amount;//MODULE NARXI

    private Double amountOnsite;//ONSITE UCHUN NARXI

    private Double oldAmount = 0d;//SKIDKADAN OLDINGI NARXI (ONLINE UCHUN)

    private Double oldAmountOnsite = 0d;//ONSITE UCHUN SKKIDKADAN OLDINSGI NARX

    private Boolean withoutTask;//TASKSIZMI? TRUE BO'LSA HAMMA DARS OCHIQ QILIANDI

    private String title;//MODULE NOMI

    @Column(columnDefinition = "text")
    private String description;//IZOH

    private Integer supportDayCount;//NECHA KUN SPPORT BERILADI
}
