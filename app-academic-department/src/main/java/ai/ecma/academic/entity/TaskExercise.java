package ai.ecma.academic.entity;


import ai.ecma.academic.entity.enums.Difficulty;
import ai.ecma.academic.entity.template.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * VAZIFA UCHUN BERILGAN TOPSHIRIQLAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskExercise extends AbstractEntity {

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Lesson lesson;//SHU VAZIFA QAYSI DARSGA TEGISHLIGI

    private String name;//NOMI

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;//QIYINLIK DARAJASI

    @OneToMany(fetch = FetchType.LAZY)
    List<Hint> hints;//MURAKKAB TASKLAR UCHUN YORDAM SIFATIDA QOYILGAN IZOH(MASLAHAT)


    @Column(columnDefinition = "text")
    private String question;//SAVOL

    private String returnType;//NIMA QAYTARISHI KERAK (BERILGAN TOPSHIRIQ YA'NI MASALA)

    @Column(columnDefinition = "text")
    @JsonIgnore
    private String ourSolution;//YECHIM,TIZIM TOMONIDAN

    @OneToMany(fetch = FetchType.LAZY)
    private List<Param> params;//KIRUVCHI PARAMETRLAR

//    public TaskExercise(Lesson lesson, String name, List<Hint> hints, String question, String returnType, String ourSolution) {
//        this.lesson = lesson;
//        this.name = name;
//        this.hints = hints;
//        this.question = question;
//        this.returnType = returnType;
//        this.ourSolution = ourSolution;
//    }
}
