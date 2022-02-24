package ai.ecma.appaccount.payload;

import ai.ecma.appaccount.entity.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
    private Integer id;

    private CurrencyType currencyType;

    private Double rate; // so'mga nisbatan kursi

    private Boolean active;
}
