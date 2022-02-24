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
import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFlow extends AbstractEntity {

    //QAYSI STUDENT EKANLIGI
    @Column(nullable = false)
    private UUID studentId;

    //STUDENT FLOW TURLARI
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private StudentFlowStatus status;

    //BOOTCAMPGA ROYXATDAN OTGAN VAQT(DOCUMENT ROYXATDAN OTKAZILGAN VAQT)
    private Timestamp docRegTime;
    //AYNAN QAYSI TIME TABLE GA TEGISHLI EKANLIGI
    // (TIME TABLE -> BIROR GURUHDA OQIYDIGAN TALABALAR ROYXATI)
    private UUID currentTimeTableId;

    //BOOTCAMP BOSHLANMI YOQMI
    private Boolean bootcampStarted;

//    public StudentFlow(User student, StudentFlowStatus status, Timestamp docRegTime) {
//        this.student = student;
//        this.status = status;
//        this.docRegTime = docRegTime;
//    }

//    public StudentFlow(User student, StudentFlowStatus status) {
//        this.student = student;
//        this.status = status;
//    }
}
