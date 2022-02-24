package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Sirojov on 02.02.2019.
 */

//PAYME UCHUN
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class OrderTransaction extends AbstractEntity {

    // PAYME DAGI TO'LOV ID SI
    private String paycomId;

    //
    @Temporal(TemporalType.TIMESTAMP)
    private Date paycomTime;

    // TRANSAKSIYANI YARATILISH VAQTI
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    // TRANSAKSIYANI BAJARILGAN VAQTI
    @Temporal(TemporalType.TIMESTAMP)
    private Date performTime;

    // TRANSAKSIYANI BEKOR QILINGAN VAQTI
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelTime;

    // BEKOR QILINISH SABABI 0 , 1, ..
    private Integer reason;

    // TRANSAKSIYA HOLATI-> NEW - 0, IN_PROGRESS - 1, ...
    private Integer state;

    // QAYSI ORDER NING TRANSAKSIYASILIGI
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private CustomerOrder order;

//    public OrderTransaction(String paycomId, Date paycomTime, Date createTime, Integer state, CustomerOrder order) {
//        this.paycomId = paycomId;
//        this.paycomTime = paycomTime;
//        this.createTime = createTime;
//        this.state = state;
//        this.order = order;
//    }
}
