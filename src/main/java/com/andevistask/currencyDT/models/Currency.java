package com.andevistask.currencyDT.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currency;
    private double rate;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Currency(LocalDate date) {
        this.date = date;
    }

    public Currency() {
    }

    public Currency(String currency, double rate, LocalDate date) {
        this.currency = currency;
        this.date = date;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "{ currency=" + currency + ' ' + "rate=" + rate + "date=" + date + " }";
    }
}
