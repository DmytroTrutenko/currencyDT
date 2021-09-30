package com.andevistask.currencyDT.controllers;

import com.andevistask.currencyDT.models.AjaxData;
import com.andevistask.currencyDT.models.ConvertForm;
import com.andevistask.currencyDT.models.Currency;
import com.andevistask.currencyDT.models.History;
import com.andevistask.currencyDT.repository.CurrencyRepository;
import com.andevistask.currencyDT.repository.HistoryRepository;
import com.andevistask.currencyDT.services.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MainController {

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CalculateService calculateService;
    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("/")
    public String hello(Model model) {
        return "hello";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Currency> currencies = currencyRepository.findAll();
        model.addAttribute("currencies", currencies);
        return "main";
    }

    @PostMapping("/main")
    public ResponseEntity<?> convert(@RequestBody ConvertForm convertForm) {
        AjaxData result = new AjaxData();
        String fc = convertForm.getfromCurrency();
        String tc = convertForm.gettoCurrency();
        double am = convertForm.getAmount();

        double convertResult = calculateService.calculateValue(fc, tc, am);

        double rate = convertResult/am;
        rate = Math.round(rate*100.0)/100.0;

        Currency c1 = currencyRepository.findByCurrency(fc);
        LocalDate DBDate = c1.getDate();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");;
        String formatDateTime = time.format(formatter);


        History history = new History(am, fc, tc, DBDate, convertResult, rate, formatDateTime);
        historyRepository.save(history);

        result.setMsg(history.toString());
        result.setConvertResult(convertResult);

        return ResponseEntity.ok(result);
    }
}