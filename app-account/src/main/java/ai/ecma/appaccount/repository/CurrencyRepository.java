package ai.ecma.appaccount.repository;

import ai.ecma.appaccount.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
}
