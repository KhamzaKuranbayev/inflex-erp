package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/*
 * Created by Sirojov on 09.02.2019.
 */

/**
 * student sotib olgan modul ning o'ziga tegishli bolgan darslari
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"}))
public class UserLesson extends AbstractEntity {
    /**
     * dars kimga tegishli ekanligi
     */
    @Column(nullable = false, name = "user_id")
    private UUID userId;

    /**
     * qaysi dars ekanligi
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    /**
     * DARSNING TUGATILGAN VAQTI TUGATILGAN VAQTI
     */
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp finishedAt;

    /**
     * student uchun darsning ochiq yopiqligi
     */
    private Boolean isOpen;

    /**
     * student uchun darsning tugatilganligi haqida
     */
    private Boolean finished = false;

    /**
     * shu dars dan qancha ball toplaganligi max 100
     */
    private Double rating;

    public UserLesson(UUID userId, Lesson lesson, Boolean isOpen, Boolean finished, Double rating) {
        this.userId = userId;
        this.lesson = lesson;
        this.isOpen = isOpen;
        this.finished = finished;
        this.rating = rating;
    }
}
