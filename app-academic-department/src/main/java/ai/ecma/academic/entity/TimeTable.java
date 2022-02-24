package ai.ecma.academic.entity;

import ai.ecma.academic.entity.enums.GroupStatus;
import ai.ecma.academic.entity.enums.TimeTableType;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * TIME TABLE (xar bir darsni bolish vaqti xonasi mentori assistenti ...)
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TimeTable extends AbstractEntity {

    /**
     * GRUHNING SANOQ BOYICHA AYNI VAQTDAGI DARSI
     */
    private Integer currentLesson = 1;

    /**
     * TIME TABLEGA BOG'LANGAN GURUH
     */
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private CourseGroup group;

    /**
     * O'QIYOTGAN KURSNING MODULI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private CoursePart coursePart;

    /**
     * TIME TABLE NI TOLDIRAYOTGAN MENTOR(GURUHNING MENTORI)
     */
    @Column(nullable = false)
    private UUID tutorId;

    /**
     * GURUHGA QAYSI MENTORLAR DARS BERGANLIGI
     */
    @ElementCollection
    private List<UUID> tutorsIdHistory;

    /**
     * TIMETABLE ASISTENTLARI
     * YOKI GURUH ASSISTENTLARI
     */
    @ElementCollection
    @CollectionTable(name = "time_table_assistant",
            joinColumns = {@JoinColumn(name = "time_table_id")})
    @Column(name = "assistant_id")
    private List<UUID> assistantsId;

    /**
     * PDP BINOSIDAGI DARS O`TISH HONASI
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Room room;

    /**
     * QAYSI KURSDA O'QISHI
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    /**
     * GURUH STATUSI (CREATED, REGISTRATION,STARTED,COMPLETED)
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupStatus status = GroupStatus.REGISTRATION;

    /**
     * TIMETABLE NOMI
     */
    @Column(nullable = false)
    private String name;

    /**
     * DARSLAR SONI
     */
    private Integer lessonCount;

    /**
     * TIME TABLENDAGI UMUMIY DARSLARNI NARXI
     */
    private Integer price;

    /**
     * GURUH HAFTANING QAYSI KUNLARIDA O'QISHI
     */
    @Column(name = "week_days", columnDefinition = "integer[]")
    @Type(type = "ai.ecma.academic.type.GenericIntArrayType")
    private Integer[] weekDays;

    /**
     * DARSNI BOSHLANISH VAQTI (SOATDA 9:00)
     */
    private String startTime;

    /**
     * DARSNI TUGASH VAQTI (SOATDA 20:00)
     */
    private String endTime;

    /**
     * TIME TABLE TURI
     */
    @Enumerated(EnumType.STRING)
    private TimeTableType timeTableType;

    /**
     * COURSE COMPLETE BO'LGANIDA O'QITUVCHIGA NECHA FOIZ BERILISHI
     */
    private Integer completeSalaryPercent;

    /**
     * TIME TABLE YOPILGAN VAQTI
     */
    private Timestamp completedDate;

//    public TimeTable(CourseGroup group, UUID tutor, List<UUID> tutorsHistory, Room room, Course course, GroupStatus status, String name, Integer lessonCount, Integer price, Integer[] weekDays, String startTime, String endTime, TimeTableType timeTableType, Integer completeSalaryPercent) {
//        this.group = group;
//        this.tutor = tutor;
//        this.tutorsIdHistory = tutorsIdHistory;
//        this.room = room;
//        this.course = course;
//        this.status = status;
//        this.name = name;
//        this.lessonCount = lessonCount;
//        this.price = price;
//        this.weekDays = weekDays;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.timeTableType = timeTableType;
//        this.completeSalaryPercent = completeSalaryPercent;
//    }


//    public TimeTable(CourseGroup group, UUID tutor, Room room, Course course, GroupStatus status, String name, Integer lessonCount, Integer price, Integer[] weekDays, String startTime, String endTime) {
//        this.group = group;
//        this.tutor = tutor;
//        this.tutorsIdHistory = new ArrayList<>();
//        this.room = room;
//        this.course = course;
//        this.status = status;
//        this.name = name;
//        this.lessonCount = lessonCount;
//        this.price = price;
//        this.weekDays = weekDays;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }

//    public TimeTable(CourseGroup group, UUID tutor, List<UUID> tutorsHistory, Room room, Course course, GroupStatus status, String name, Integer lessonCount, Integer price, Integer[] weekDays, String startTime, String endTime, TimeTableType timeTableType) {
//        this.group = group;
//        this.tutor = tutor;
//        this.tutorsIdHistory = tutorsHistory;
//        this.room = room;
//        this.course = course;
//        this.status = status;
//        this.name = name;
//        this.lessonCount = lessonCount;
//        this.price = price;
//        this.weekDays = weekDays;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.timeTableType = timeTableType;
//    }

//    public TimeTable(CourseGroup group, UUID tutor, List<UUID> tutorsHistory, Room room, Course course, GroupStatus status, String name, Integer lessonCount, Integer price, Integer[] weekDays, String startTime, String endTime, TimeTableType timeTableType, List<UUID> assistants) {
//        this.assistantsId = assistants;
//        this.group = group;
//        this.tutor = tutor;
//        this.tutorsIdHistory = tutorsHistory;
//        this.room = room;
//        this.course = course;
//        this.status = status;
//        this.name = name;
//        this.lessonCount = lessonCount;
//        this.price = price;
//        this.weekDays = weekDays;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.timeTableType = timeTableType;
//    }

}
