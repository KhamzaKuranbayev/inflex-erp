package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * BOOTCAMP UCHUN TO'LOV
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BootcampPayment extends AbstractEntity {

    /**
     * QAYSI BOOTCAMPGA EKANLIGI
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Bootcamp bootcamp;
    /**
     * KIM TOMONIDANLIGI
     */
    @Column(nullable = false)
    private UUID userID;

    /**
     * BOOTCAMPNI TUGATGANMI YOKI TUGATMAGANMI
     */
    @Column(nullable = false)
    private Boolean isFinished;

    /**
     * TUGATGAN VAQTI
     */
    private Timestamp finishedTime;

    /**
     * OQISH TURI
     */
//    @Enumerated(EnumType.STRING)
//    private StudyType studyType;//2XIL OQISH TURI BOR, 1-ONLINE, 2-ONSITE

    /**
     * GRANDMI YOKI YOQMI
     * GRAND BO'LSA BOOTCAMPDA O'QISH TEKIN BO'LADI
     * VA BOOTCAMPNI TUGATIB ISHLAB BERISHI KERAK, 1YIL YOKI 2YIL
     */
    private Boolean isGrand;

    /**
     * TO'LANILAYOTGAN PUL HAJMI
     */
    private Integer amount;

    /**
     * GRAND TURI
     * BOOTCAMPDA TEKIN O'QIB KEYIN 1YILDA YOKI 2YIL ISHLAB TO'LAB BERISHINI TURI
     */
//    @Enumerated(EnumType.STRING)
//    private GrandType grandType; //2XIL TURI BOR, 1 - ONE_YEAR, 2 - TWO_YEAR

}
