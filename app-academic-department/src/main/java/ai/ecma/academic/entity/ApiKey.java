package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * SISTEMADAGI CONSTANTALARNI BOSHQARISH UCHUN ISHLATILADIGAN ENTITY
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApiKey extends AbstractEntity {

    /**
     * BOOMSTREAM VIDEO PLATFORMASI UCHUN ISHLATILGAN
     */
    @Column(nullable = false)
    private String apiKey;

    /**
     * BOOMSTREAM VIDEO PLATFORMASI UCHUN ISHLATILGAN
     */
    @Column(nullable = false)
    private String ppvCode;

    private Boolean onlineKonsultatsiya = true;

    /**
     * KURS SOTIB OLISH UCHUN CUPON FOIZI
     */
    private Integer buyCoursePercentForCoupon;

    /**
     * STUDENT FILE YUKLASHIDAGI LIMIT
     */
    private Integer studentMaxFileSize;

    /**
     * RUBL KURSI
     */
    private Double rublKurs;
    private Integer hours;

    /**
     * OYLIK UCHUN FOIZ
     * USHBU KURSNI YOZGAN MENTOR KURSDAN OLADIGAN OYLIK FOIZI(ANIQMAS)
     */
    private Integer percentForSalary;

    /**
     * ISH VAQTINI BOSHLANISHI
     */
    private String startWorkingTime;

    /**
     * ISH VAQTINING TUGASHI
     */
    private String endWorkingTime;

    /**
     * HAFTANIGN QAYSI KUNLARI ISH KUNLARI EKANLIGINI BELGILANADI
     */
    @Column(name = "working_days", columnDefinition = "integer[]")
    @Type(type = "ai.ecma.academic.type.GenericIntArrayType")
    private Integer[] workingDays;
    /**
     *
     */
    private Boolean cashback;
    /**
     * BIR KUNLIK ACADEMIC SUPPORTNING SOTIB OLISH
     * UCHUN NARXI
     */
    private Double oneDaySupportPrice;
    /**
     * FINANCE PLATFORMASIGA TEGISHLIK BO'LGAN URL
     */
    private String financeUrl;
}
