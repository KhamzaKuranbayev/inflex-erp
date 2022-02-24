package ai.ecma.appaccount.entity;

import ai.ecma.appaccount.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cashbox extends AbstractEntity {


    private String name;

    private Boolean active = true;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Currency currency;
}
