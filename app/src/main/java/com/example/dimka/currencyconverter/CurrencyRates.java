package com.example.dimka.currencyconverter;

import java.util.LinkedList;

public class CurrencyRates {

    private final String date;
    private final String bank;
    private final LinkedList<LinkedList<Float>> datas;

    public CurrencyRates(String date,
                         String bank,
                         LinkedList<LinkedList<Float>> datas) {
        this.date = date;
        this.bank = bank;
        this.datas = datas;
    }

    public LinkedList<LinkedList<Float>> getDatas() {
        return datas;
    }

    public String getBank() {
        return bank;
    }

    public String getDate() {
        return date;
    }
}
