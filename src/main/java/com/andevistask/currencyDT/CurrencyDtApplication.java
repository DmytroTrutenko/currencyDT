package com.andevistask.currencyDT;


import com.andevistask.currencyDT.models.Currency;
import com.andevistask.currencyDT.repository.CurrencyRepository;
import com.andevistask.currencyDT.services.DataFromXML;
import com.andevistask.currencyDT.services.ParsingXMLService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

public class CurrencyDtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyDtApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CurrencyRepository currencyRepository){
        // When the application is launched, the current exchange rates are read from the European Central Bank website using the XML parsing service
        DataFromXML data = ParsingXMLService.parsingXML();
        return args -> {
            for (Currency c:data.getCurrencies())
                currencyRepository.save(c);
        };
    }
}
