package ai.ecma.appaccount.entity;

import ai.ecma.appaccount.entity.enums.PayTypeEnum;
import ai.ecma.appaccount.entity.template.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Sirojov on 01.02.2019.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PayType extends AbstractEntity {
    private String name;

    @Enumerated(value = EnumType.STRING)
    private PayTypeEnum payTypeEnum;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Cashbox cashBox;
}
