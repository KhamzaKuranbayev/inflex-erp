package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.EduTypeEnum;
import ai.ecma.academic.entity.enums.StudyType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * PAYME VA CLICK UCHUN ORDER
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder extends AbstractEntity {
    private Integer amount;

    private Boolean delivered;

    private UUID userId;

    private Boolean transactionCreated = false;

    @ElementCollection
    private List<String> parts;//MODULELAR LISTI

    private UUID courseId;//QAYSI KURS UCHUN

    private String vaucherCode;//VOUCHER KODI

    @Enumerated(value = EnumType.STRING)
    private StudyType studyType;//TALIM TURI

    private UUID roadmapId;//ROADMAP IDSI

    private UUID timeTableId;//QAYSI TIMETABLE UCHUN()

    @Enumerated(value = EnumType.STRING)
    private EduTypeEnum eduType;//QAYSI TA'LIM UCHUN

    @Column(name = "payment_status")
    private Boolean paymentStatus = false;//TO'LOV HOLATI

    public CustomerOrder(Integer amount, Boolean delivered, UUID userId, Boolean transactionCreated, List<String> parts, UUID courseId, String vaucherCode, StudyType studyType, UUID roadmapId, UUID timeTableId, EduTypeEnum eduType) {
        this.amount = amount;
        this.delivered = delivered;
        this.userId = userId;
        this.transactionCreated = transactionCreated;
        this.parts = parts;
        this.courseId = courseId;
        this.vaucherCode = vaucherCode;
        this.studyType = studyType;
        this.roadmapId = roadmapId;
        this.timeTableId = timeTableId;
        this.eduType = eduType;
    }

    public CustomerOrder(int amount, boolean transactionCreated, UUID userId, boolean paymentStatus, EduTypeEnum eduType) {
        this.amount = amount;
        this.transactionCreated = transactionCreated;
        this.userId = userId;
        this.paymentStatus = paymentStatus;
        this.eduType = eduType;
    }
}
