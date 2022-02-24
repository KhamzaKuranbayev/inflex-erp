package ai.ecma.academic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ai.ecma.academic.entity.template.AbstractEntity;


import javax.persistence.*;
import java.util.Map;

/**
 * ONLINE PLATFORMADAGI MODULLARNING ICHIDAGI DARSLAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends AbstractEntity {

    @Column(nullable = false)
    private Integer ord;//LESSON NING TARTIBI RAQAMI HAR BIR MODULDAGI
    // JAVA CORE BASIC MODULIDAGI ORD=1 BO'LGAN DARS (Kirish. Java dasturlash tili. Dasturlash muhitini sozlash)

    @Column(nullable = false, unique = true)
    private String url;//   LESSON NING URL I

    @Column(nullable = false)
    private String title;//  LESSON NOMI

    @Column(columnDefinition = "text")
    private String description;// AYNAN SHU LESSON UCHUN IZOH

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CoursePart coursePart;//LESSON NING QAYSI MODULE GA TEGISHLI EKANLIGI

    @ElementCollection
    private Map<String, String> usefulLinks;
    //LESSON NI  YANADA YAXSHI TUSHUNISH UCHUN BERILGAN  FOYDALI LINKLAR TITLE VA URLI
}
