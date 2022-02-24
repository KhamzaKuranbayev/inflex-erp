package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * MUTAXXASISLIK KURSLARI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roadmap extends AbstractEntity {

    /**
     * ROADMAP NOMI
     */
    @Column(nullable = false)
    private String title;

    /**
     * ROADMAP TARIFI
     */
    @Column(columnDefinition = "text")
    private String description;

    /**
     * ROADMAP O'Z ICHIGA OLADIGAN KURSLAR
     */
    @OneToMany(fetch = FetchType.LAZY)
    private List<RoadmapCourses> roadmapCourses;

    /**
     * URL (MANZIL)
     */
    @Column(nullable = false, unique = true)
    private String url;

    /**
     * AKTIV YOKI AKTIV EMASLIK STATUSI
     */
    @Column(nullable = false)
    private Boolean active = false;

    /**
     * TARTIB RAQAMI
     */
    @Column(nullable = false, unique = true)
    private Integer ord;

    /**
     * ONLINE MI?
     */
    @Column(nullable = false)
    private Boolean online = false;

    /**
     * ONSITE MI?
     */
    @Column(nullable = false)
    private Boolean onsite = false;

    /**
     * ONLINE DAGISI UCHUN NARXI
     */
    @Column(nullable = false)
    private Integer allAmountOnline;

    /**
     * ONSITE DAGISI UCHUN NARXI
     */
    @Column(nullable = false)
    private Integer allAmountOnsite;

    /**
     * BOG'LANGAN BOOTCAMP
     */
    @OneToOne(mappedBy = "roadmap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Bootcamp bootcamp;

    /**
     * ROADMAP GA BERILGAN RASM HAR BIRIGA ALOHIDA
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment metaImg;

    /**
     * TEZ KUNDA
     */
    private Boolean comingSoon;

    /**
     * IZOH
     */
    @Column(columnDefinition = "text")
    private String shortDescription;
}