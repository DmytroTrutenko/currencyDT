package com.andevistask.currencyDT.repository;

import com.andevistask.currencyDT.models.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Currency findByCurrency(String currency);
}
