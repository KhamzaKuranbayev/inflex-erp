package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/*
KURSLAR UCHUN
SPRING KURSI, DJANGO KURSI, REACT KURSI, FRONTEND KURSI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String title;//KURS NOMI

    @Column(unique = true, nullable = false)
    private String url;//SAYTDA SHU URLDA CHIQADI
    // VA BACKENDDAN HAM SHU URL BO'YICHA OLIB KETADI

    @Column(columnDefinition = "text")
    private String description;//IZOH

    private String introVideoUrl;//INTRO VIDEONIN
    // G URLI

    //TODO BU HOZIRDA BITTA KURSGA BITTA MENTOR. QUERYLARNI SHUNGA MOSLASH KERAK
    @ElementCollection
    @CollectionTable(name = "course_tutor", joinColumns = {@JoinColumn(name = "course_id")})
    @Column(name = "user_id")
    private List<UUID> tutors;//MENTORLAR LISTI

    @ElementCollection
    @CollectionTable(name = "course_assistant", joinColumns = {@JoinColumn(name = "course_id")})
    @Column(name = "user_id")
    private List<UUID> assistants;//ASISITENTLAR LISTI

    private Boolean active;//SHU KURS ACTIVEMI?

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;//QAYSI COMPANYDAGI MENTOR O'TADI

    private Boolean online;//ONLINEDA BORMI?

    private Boolean onsite;//ONSITEDA BORMI?    1

    private Boolean offline;//OFFLINEDA BORMI?

    private Boolean stopSalary;//O'QITUVCHIGA OYLIK TO'LANMAYDI

    private Boolean comingSoon;//TEZ KUNDA CHIQADI DEGANI

    private Integer offlineDuration;//OFFLINE SHU KURS QANCHA MUDDAT O'QITILADI?

    private Integer discountAmountOnline;//SHU KURSNI HAMMA MODULELARINI ONLINE BIRDANIGA SOTIB OLSA QANCHAGA TUSHISHI

    private Integer discountAmountOnsite;//SHU KURSNI HAMMA MODULELARINI ONSITE BIRDANIGA SOTIB OLSA QANCHAGA TUSHISHI

    private String metaTitle;//SEO UCHUN TITLE

    @Column(columnDefinition = "text")
    private String metaDescription;//SEO UCHUN

    private String canonicalUrl;//SEO UCHUN

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment metaImg;//SEO UCHUN
}
