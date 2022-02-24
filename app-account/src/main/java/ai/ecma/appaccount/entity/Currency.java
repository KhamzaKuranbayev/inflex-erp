package ai.ecma.appaccount.entity;

import ai.ecma.appaccount.entity.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private CurrencyType currencyType;

    private Double rate; // so'mga nisbatan kursi

    private Boolean active;

}
