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

/**
 * TASK HAQIDA FAQ(Frequently Asked Questions)
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFaq extends AbstractEntity {

    private String title;//SAVOL SARLAVHASI NOMI

    @Column(columnDefinition = "text")
    private String text;//SAVOL TEXT

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;//MALUM FAYLLAR

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Task task;//QAYSI VAZIFAGA TEGISHLI EKANLIGI VAZIFA

}
