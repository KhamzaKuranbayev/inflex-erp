package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
/*
PAYNET  UCHUN TRANZAKSIYA
 */
public class PaynetTransaction extends AbstractEntity {

    private Long amount; //TO`LOV SUMMASI

    private Long serviceId;  //SERVISNING ID si

    @Column(unique = true)
    private Long transactionId; //TRANZAKSIYA ID si

    private Timestamp transactionTime;//USHBU AMAL BAJARILGAN VAQT

    private String phoneNumber;//STUDENTNING TELEFON RAQAMI

    private Integer state;//1 YOKI 0 KELADI BU TO'LOV QANDAY AMALGA OSHGANLIGINI BILDIRADI(STATUSI DESA HAM BO'LADI)

}
