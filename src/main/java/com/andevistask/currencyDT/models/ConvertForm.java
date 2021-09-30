package com.andevistask.currencyDT.models;

public class ConvertForm {
    private String fromCurrency;
    private String toCurrency;
    private double amount;

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getfromCurrency() {
        return fromCurrency;
    }

    public String gettoCurrency() {
        return toCurrency;
    }

    public double getAmount() {
        return amount;
    }
}