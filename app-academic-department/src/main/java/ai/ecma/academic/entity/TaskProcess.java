package ai.ecma.academic.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ai.ecma.academic.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

/**
 * VAZIFANING TOPSHIRIG'I UCHUN PRECESS
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskProcess extends AbstractEntity {

    @Column(nullable = false)
    private UUID userId;//STUDENT

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TaskExercise taskExercise;//TOPSHIRIQ

    private boolean completed;//BAJARILGANMI

    private String method;//int,double,String .etc

}
