package com.andevistask.currencyDT.services;

import com.andevistask.currencyDT.models.Currency;
import com.andevistask.currencyDT.models.History;
import com.andevistask.currencyDT.repository.CurrencyRepository;
import com.andevistask.currencyDT.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

@Service
public class CalculateService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    HistoryRepository historyRepository;



    public double calculateValue(String fromCurrency,  String toCurrency, double amount){

        Currency c1 = currencyRepository.findByCurrency(fromCurrency);
        double fromRate = c1.getRate();
        Currency c2 = currencyRepository.findByCurrency(toCurrency);
        double toRate = c2.getRate();

        LocalDate actualDate = ParsingXMLService.getActualDate();
        LocalDate DBDate = c1.getDate();
        DataFromXML data;

        if(!actualDate.equals(DBDate)){
            currencyRepository.deleteAll();
            data = ParsingXMLService.parsingXML();
            for (Currency c:data.getCurrencies())
                    currencyRepository.save(c);
        }

        double res = ((toRate/ fromRate)*amount);
        res = Math.round(res*100.0)/100.0;

        return res;
    }
}
