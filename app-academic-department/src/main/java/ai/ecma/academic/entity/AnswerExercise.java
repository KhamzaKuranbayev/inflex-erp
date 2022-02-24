package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

//AVTOMAT MASALANI YECHADIGAN TOOL UCHUN JAVOBLAR
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnswerExercise extends AbstractEntity {

//    @ManyToOne(fetch = FetchType.LAZY)
//    private TaskExercise taskExercise;//MASALA

    @OneToMany(fetch = FetchType.LAZY)
    private List<ParamValue> values;//KIRUVCHI PARAMTERLAR

    private String result;//NATIJA
}
