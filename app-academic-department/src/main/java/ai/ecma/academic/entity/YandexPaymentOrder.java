package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.EduTypeEnum;
import ai.ecma.academic.entity.enums.StudyType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * YANDEX ORQALI TO'LOV AMALGA OSHIRISH UCHUN
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YandexPaymentOrder extends AbstractEntity {
    /**
     * YANDEXNI OZIDAN KELADIGAN PROPERTY
     */
    @Column(nullable = false)
    private String requestId;
    /**
     * KIM TOMONIDAN TO'LOV AMALGA OSHIRILAYOTGANI
     */
    @Column(nullable = false)
    private UUID userId;
    /**
     * TO'LOV QILISH JARAYONIDAGI STATUS
     */
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private Double amount;//SUMMA SO'MDA
    @Column(nullable = false)
    private Double amountRubl;//SUMMA RUBLDA
    /**
     * YANDEXDAN KELADIGAN QIYMAT
     */
    private String instanceId;
    /**
     * AMALGA OSHIRILAYOTGAN TO'LOV
     */
    /*@ManyToOne(fetch = FetchType.LAZY)
    private CustomerOrder order;*/
    /**
     * O'QUV TURI, YA'NI QANDAY OQISH UCHUNLIGI, ONSITE OR ONLINE
     */
    @Enumerated(EnumType.STRING)
    private StudyType studyType;

    /**
     * QAYSI O'QUV TURIGA TO'LOV QILINAYOTGANI(BOOTCAMP,ROADMAP ECT)
     */
    @Enumerated(EnumType.STRING)
    private EduTypeEnum eduType;

}
