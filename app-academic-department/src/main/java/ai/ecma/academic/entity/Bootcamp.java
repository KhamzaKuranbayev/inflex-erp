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
import javax.persistence.OneToOne;
import java.sql.Timestamp;

/**
 * PLATFORMADAGI BOOTCAMPLAR SHU ENTITY ORQALI SAQLANADI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bootcamp extends AbstractEntity {
    /**
     * BOOTCAMPNING NOMI
     */
    @Column(nullable = false, unique = true)
    private String title;

    /**
     * BOOTCAMPNING URL
     */
    @Column(nullable = false, unique = true)
    private String url;

    /**
     * BOOTCAMPGA QO'SHIMCHA MALUMOT TO'LDIRISH UCHUN
     */
    @Column(columnDefinition = "text")
    private String description;

    /**
     * BITTA BOOTCAMPGA BITTADAN ROADMAP BIRIKTIRISH.
     * HAR QANDAY BOOTCAMPDA QAYSIDIR ROADMAP BO'LADI.
     * EXAMPLE: JAVA BOOTCAMP
     * (JAVA FULL STACK ROADMAP (FRONTEND DEVELOPMENT, REACT DEVELOPMENT,
     * JAVA DEVELOPMENT, DATABASE DEVELOPMENT, SPRING DEVELOPMENT))
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Roadmap roadmap;

    /**
     * BU BOOTCAMP SINOV OYI BORMI YOKI YO'QMI BILISH UCHUN
     */
    @Column(nullable = false)
    private Boolean tryMonth = false;

    /**
     * ACTIVE YOKI ACTIVE EMASLIGI
     */
    @Column(nullable = false)
    private Boolean active = false;

    /**
     * QAYSI TARTIBDA BO'LISHI
     */
    private Integer ord;

    /**
     * BOOTCAM UCHUN RASMLAR QO'YILISHI MUMKIN
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment metaImg;

    /**
     * CHEGIRMA FOIZI
     */
    private Integer discountPercent;

    /**
     * CHEGIRMALAR TUGASH VAQTI
     */
    private Timestamp discountExpireDate;

    /**
     * YAQIN ORADA OCHILADIGANMI YO'QMI BILDIRISH
     */
    private Boolean comingSoon;

    @Column(nullable = false)
    private Boolean isDefault = false;

    public Bootcamp(String title, String url, String description, Roadmap roadmap, Boolean tryMonth, Boolean active, Integer ord) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.roadmap = roadmap;
        this.tryMonth = tryMonth;
        this.active = active;
        this.ord = ord;
    }

    public Bootcamp(String title, String url, String description, Roadmap roadmap, Boolean tryMonth, Boolean active, Integer ord, Integer discountPercent, Timestamp discountExpireDate) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.roadmap = roadmap;
        this.tryMonth = tryMonth;
        this.active = active;
        this.ord = ord;
        this.discountPercent = discountPercent;
        this.discountExpireDate = discountExpireDate;
    }

    public Bootcamp(String title, String url, String description, Roadmap roadmap, Boolean tryMonth, Boolean active, Integer ord, Integer discountPercent, Timestamp discountExpireDate, Boolean comingSoon) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.roadmap = roadmap;
        this.tryMonth = tryMonth;
        this.active = active;
        this.ord = ord;
        this.discountPercent = discountPercent;
        this.discountExpireDate = discountExpireDate;
        this.comingSoon = comingSoon;
    }
}
