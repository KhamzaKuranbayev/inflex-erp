package ai.ecma.appaccount.service;

import ai.ecma.appaccount.entity.Currency;
import ai.ecma.appaccount.payload.CurrencyDto;
import ai.ecma.appaccount.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    @Autowired
    DtoService dtoService;

    @Autowired
    CurrencyRepository currencyRepository;

    public CurrencyDto saveCurrency(CurrencyDto dto){
        try {
            Currency currency = new Currency();
            currency.setCurrencyType(dto.getCurrencyType());
            currency.setRate(dto.getRate());
            currency.setActive(dto.getActive());
            Currency savedCurrency = currencyRepository.save(currency);
            return dtoService.currencyDto(savedCurrency);
        }catch (Exception e){
            return null;
        }
    }

    public List<CurrencyDto> getAll() {
        List<Currency> all = currencyRepository.findAll();
        return all.stream().map(item->dtoService.currencyDto(item)).collect(Collectors.toList());
    }
}
