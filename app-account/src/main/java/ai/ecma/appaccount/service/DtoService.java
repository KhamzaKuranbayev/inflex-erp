package ai.ecma.appaccount.service;

import ai.ecma.appaccount.entity.Currency;
import ai.ecma.appaccount.payload.CurrencyDto;
import org.springframework.stereotype.Service;

@Service
public class DtoService {

    public CurrencyDto currencyDto(Currency currency){
        return new CurrencyDto(currency.getId(), currency.getCurrencyType(), currency.getRate(), currency.getActive());
    }
}
