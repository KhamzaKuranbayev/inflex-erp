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
 * DARS BO'LIMLARI YANI LESSONNI VIDEOLARI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Part extends AbstractEntity {
    @Column(nullable = false)
    private Integer ord;//TARTIB RAQAMI misol : 1.2.1

    @Column(nullable = false, columnDefinition = "text")
    private String title;//NOMI MISOL: JSX sintaksisi

    @Column(columnDefinition = "text")
    private String description;//IZOH

    @Column(nullable = false)
    private String mediaCode;//MEDIA NI OLIB KELISH UCHUN BOOMSTREAMGA YUBORILADIGAN CODE

    @Column(nullable = false)
    private String mediaUrl;//MEDIA NI OLISH UCHUN URL

    private Boolean isYoutube = false;//YOUTUBE DANMII videoni youtubedan olib kelish kkmi? yoki yo'q

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Lesson lesson;//QAYSI LESSON GA TEGISHLI EKANLIGI

}
