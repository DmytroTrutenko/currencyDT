package com.andevistask.currencyDT.services;

import com.andevistask.currencyDT.models.Currency;

import java.util.List;

public class DataFromXML {
    private List<Currency> currencies;

    public DataFromXML(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

}
