package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.CoursePaymentFrom;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * MODULENI SOTIB OLINGAN PAYTDA YOZILADI
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"part_id", "userId"}))
public class CoursePayment extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CoursePart part;//QAYSI MODULENI SOTIB OLDI

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;//QAYSI KURSDAN SOTIB OLDI

    private Double amount;//QANCHAGA SOTIB OLDI?

    private Double oldAmount;// O'ZINI ASL NARXI(SKIDKADAN OLDINGI)
    //SKIDKA BOR YOKI YOQLIGI HAQIDA BOOLEAN YO'Q)

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Column(nullable = false)
    private UUID userId;//MODULNI  SOTB OLGAN

    private Boolean isFinished = false;//MODULNI TUGATDIMI?

    private Boolean stop = false;//MODULNI OXORIGA YETKAZMASDAN TO'XTATGANMI?

    private Timestamp fullCourseFinishedTime;//KURSNI TO'LIQ TUGATGAN VAQTI

    @Enumerated(EnumType.STRING)
    private CoursePaymentFrom coursePaymentFrom = CoursePaymentFrom.COURSE;//QAYSI PAGEDAN SOTIB OLDI?

    @Enumerated(EnumType.STRING)
    private StudyType studyType;//SOTIB OLGAN  TA'LIM TURI(ONLINE OFFLINE)
@Column(nullable = false)
    private UUID parentId;//QAYSI TURDAN OLGANLIGI? BOOTCAMPNI IDSI, COURSNI ID SI VA H.K(ROAD MAP IDSI)

    private Timestamp moduleFinished;//MODULENI QACHON TUGATDI?

    private Integer salaryPercent;//MENTORGA QANCHA FOIZ BERILADI?

    private Integer stopDayLeft;//STOPNI BOSGAN PAYTDAN KEYIN NECHA KUN SUPPORTI QOLGAN EDI?

    private Timestamp endDate;//QACHON SUPPORT TUGAYDI

    private Timestamp stoppedAt;//STOPNI BOSGAN VAQTI

    private Timestamp startedAt;//MODULNI BOSHLAGAN VAQTI

    @Column(nullable = false)
    private Boolean buyedFromCourse = false;//KURSDAN SOTIB OLINGANMI

    @Column(nullable = false)
    private Boolean buyedFromRoadmap = false;//ROADMAP DAN SOTIB OLINGANMI

    @Column(nullable = false)
    private Boolean buyedFromBootcamp = false;//BOOTCAMPDAN SOTIB OLINGANMI

    public CoursePayment(CoursePart part, Course course, Double amount, Double oldAmount, UUID userId, Boolean isFinished, Boolean stop, Timestamp fullCourseFinishedTime, CoursePaymentFrom coursePaymentFrom, StudyType studyType, UUID parentId, Boolean buyedFromBootcamp) {
        this.part = part;
        this.course = course;
        this.amount = amount;
        this.oldAmount = oldAmount;
        this.userId = userId;
        this.isFinished = isFinished;
        this.stop = stop;
        this.fullCourseFinishedTime = fullCourseFinishedTime;
        this.coursePaymentFrom = coursePaymentFrom;
        this.studyType = studyType;
        this.parentId = parentId;
        this.buyedFromBootcamp = buyedFromBootcamp;
    }

    public CoursePayment(CoursePart part, Course course, Double amount, Double oldAmount, UUID userId, Boolean isFinished, Boolean stop, Timestamp fullCourseFinishedTime, CoursePaymentFrom coursePaymentFrom, StudyType studyType, UUID parentId, Timestamp endDate) {
        this.part = part;
        this.course = course;
        this.amount = amount;
        this.oldAmount = oldAmount;
        this.userId = userId;
        this.isFinished = isFinished;
        this.stop = stop;
        this.fullCourseFinishedTime = fullCourseFinishedTime;
        this.coursePaymentFrom = coursePaymentFrom;
        this.studyType = studyType;
        this.parentId = parentId;
        this.endDate = endDate;
    }
}
