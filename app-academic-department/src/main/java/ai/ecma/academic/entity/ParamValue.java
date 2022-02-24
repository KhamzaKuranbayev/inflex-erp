package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ParamValue extends AbstractEntity {


    //String name - kelsa uning qiymati saqlanadi ya'ni "Abdurashid"
    //parametrni qiymati
    private String dataValue;
}
