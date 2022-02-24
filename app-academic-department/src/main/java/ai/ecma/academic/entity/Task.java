package ai.ecma.academic.entity;


import ai.ecma.academic.entity.enums.TaskPriorityEnum;
import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * HAR BIR LESSON UCHUN BERILADIGAN TOPSHIRIQLAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task extends AbstractEntity {

    @Column(nullable = false)
    private Integer ord;//TARTIB RAQAMI

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Lesson lesson;//QAYSI DARSGA TEGISHLI EKANLIGI

    @Enumerated(EnumType.STRING)
    private TaskPriorityEnum taskPriority;//VAZIFANI QIYINLIK DARAJASI

    private Boolean mustComplete;//QILINISHI SHARTMI YOKI YOQ(MAJBURIY VA XOXISHIY VAZIFALAR BO'LISHI MUMKIN)

    @Column(nullable = false)
    private String title;//TASK NOMI

    @Column(columnDefinition = "text")
    private String description;//IZOH

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attachment> files;//SHU TASK UCHUN MO'LJALLANGAN FAYLLAR

    private Boolean bootcamp;//TASK BOOTCAMPNIKI YO ODDIY KURSNIKI LIGINI ANIQLASH

//    public Task(Integer ord, String title, String description, List<Attachment> files, Lesson lesson) {
//        this.ord = ord;
//        this.title = title;
//        this.description = description;
//        this.files = files;
//        this.lesson = lesson;
//    }

//    public Task(Integer ord, Lesson lesson, TaskPriorityEnum taskPriority, Boolean mustComplete, String title, String description, List<Attachment> files) {
//        this.ord = ord;
//        this.lesson = lesson;
//        this.taskPriority = taskPriority;
//        this.mustComplete = mustComplete;
//        this.title = title;
//        this.description = description;
//        this.files = files;
//    }
}
