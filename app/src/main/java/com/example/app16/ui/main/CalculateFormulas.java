package com.example.app16.ui.main;
/*
This class is responsible for performing all the required calculations
Methods to add SMA, EMA, MACD, MACDAVG
 */

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateFormulas {

    ArrayList<String> timeFrames;
    ArrayList<Double> stockValues;
    ArrayList<Price> priceDateList;
    private int interval;

    public CalculateFormulas() {

    }

    public CalculateFormulas(ArrayList[] timeFrameAndValues, int interval) {
        this.timeFrames = (ArrayList<String>) timeFrameAndValues[0];
        this.stockValues = (ArrayList<Double>) timeFrameAndValues[1];
        this.priceDateList = (ArrayList<Price>) timeFrameAndValues[2];
        this.interval = interval;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList[] calcForInstrument(IndicatorsEnum type) {

        switch (type) {
            case SMA:
                return calculateSMA();
            case EMA:
                return calculateEMA();
            case MACD:
                return calculateMACD();
            case MACDAVG:
                return calculateMACDAVG();
        }
        return null;
    }

    // SMA calculation
    private ArrayList[] calculateSMA() {
        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }
        int dateInterval = timeFrames.size() / interval;
        int index = dateInterval;

        while (index <= timeFrames.size()) {
            lists[0].add(timeFrames.get(index - 1));
            lists[1].add(sumValues(index - dateInterval, index) / dateInterval);
            index += dateInterval;
        }
        return lists;
    }

    // EMA = (today’s closing price *K) + (Previous EMA * (1 – K))
    // K (Smoothing Factor) = 2/(N+1)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList[] calculateEMA() {
        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }

        List<Price> emaList = getEMAValues(priceDateList, 20);
        Collections.reverse(emaList);
        List<String> dateList = emaList
                .stream()
                .map(p -> p.getDateOfStock().format(DateTimeFormatter.ofPattern("dd-MMM-yy"))
                        .substring(3,9))
                .collect(Collectors.toList());
        List<Double> stockPriceList = emaList.stream()
                .map(Price::getStockPrice)
                .collect(Collectors.toList());
        int dateInterval = dateList.size() / interval;
        int index = dateInterval;
        while (index <= dateList.size()) {
            lists[0].add(dateList.get(index - 1));
            lists[1].add(stockPriceList.get(index - 1));
            index += dateInterval;
        }

        return lists;

    }

    // MACD=12-Period
    // EMA − 26-Period EMA
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList[] calculateMACD() {

        List<Price> macdList = getMACDValues();

        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }

        List<String> dateList = macdList
                .stream()
                .map(p -> p.getDateOfStock().format(DateTimeFormatter.ofPattern("dd-MMM-yy"))
                        .substring(3,9))
                .collect(Collectors.toList());
        List<Double> stockPriceList = macdList.stream()
                .map(Price::getStockPrice)
                .collect(Collectors.toList());
        int dateInterval = dateList.size() / interval;
        int index = dateInterval;
        while (index <= dateList.size()) {
            lists[0].add(dateList.get(index - 1));
            lists[1].add(stockPriceList.get(index - 1));
            index += dateInterval;
        }

        return lists;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList[] calculateMACDAVG() {
        List<Price> macdValues = getMACDValues();
        List<Price> macdAvgList = getEMAValues(macdValues, 9);
        Collections.reverse(macdAvgList);
        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }

        List<String> dateList = macdAvgList
                .stream()
                .map(p -> p.getDateOfStock().format(DateTimeFormatter.ofPattern("dd-MMM-yy"))
                        .substring(3,9))
                .collect(Collectors.toList());
        List<Double> stockPriceList = macdAvgList.stream()
                .map(Price::getStockPrice)
                .collect(Collectors.toList());

        int dateInterval = dateList.size() / interval;
        int index = dateInterval;
        while (index <= dateList.size()) {
            lists[0].add(dateList.get(index - 1));
            lists[1].add(stockPriceList.get(index - 1));
            index += dateInterval;
        }

        return lists;
    }

    private List<Price> getMACDValues() {
        List<Price> _12emaValues = getEMAValues(priceDateList, 12);
        List<Price> _26emaValues = getEMAValues(priceDateList, 26);
        List<Price> macdList = new ArrayList<>();
        for (Price _26ema : _26emaValues) {
            for (Price _12ema : _12emaValues) {
                if (_12ema.getDateOfStock().equals(_26ema.getDateOfStock())) {
                    Price price = new Price();
                    Double emaValue = (double) _12ema.getStockPrice() - _26ema.getStockPrice();
                    price.setStockPrice(emaValue);
                    price.setDateOfStock(_12ema.getDateOfStock());
                    macdList.add(price);
                }
            }
        }
        return macdList;
    }


    //EMA=Price(t)×k+EMA(y)×(1−k)
    //=today
    //y=yesterday
    //N=number of days in EMA
    //k=2÷(N+1)
    //​
    public List<Double> sumEMAvalues(List<Double> stockList, int period) {

        double k = (double) 2 / (period + 1);
        k = Math.round(k * 100) / 100.0d;
        List<Double> emaList = new ArrayList<>();
        double smaSum = stockList
                .subList(stockList.size() - period - 1, stockList.size() - 1)
                .stream()
                .reduce(0.0, Double::sum) / period;
        double ema = 0.0;
        for (int index = stockList.size() - 1 - period; index >= 0; index--) {

            if (emaList.isEmpty()) {
                ema = (stockList.get(index) * k) + smaSum * (1 - k);
            } else {
                ema = (stockList.get(index) * k) + ema * (1 - k);
            }
            ema = Math.round(ema * 100) / 100.0d;
            emaList.add(ema);
        }
        return emaList;
    }

    public double sumValues(int x, int y) {
        double sum = 0;
        for (int i = x; i < y; i++) {
            sum += stockValues.get(i);
        }
        return (sum);
    }


    public List<Price> getEMAValues(List<Price> priceList, int period) {

        Collections.reverse(priceList);

        double k = (double) 2 / (period + 1);
        k = Math.round(k * 100) / 100.0d;
        List<Price> emaList = new ArrayList<>();
        List<Price> smaSubList = priceList
                .subList(0, period - 1);
        double smaSum = 0.0;
        for (Price p : smaSubList) {
            smaSum += p.getStockPrice();
        }
        smaSum = (double) smaSum / period;
        double ema = 0.0;
        for (int i = period; i < priceList.size(); i++) {
            if (emaList.isEmpty()) {
                ema = (priceList.get(i).getStockPrice() * k) + smaSum * (1 - k);
            } else {
                ema = (priceList.get(i).getStockPrice() * k) + ema * (1 - k);
            }
            ema = Math.round(ema * 100) / 100.0d;
            Price emaPrice = new Price();
            emaPrice.setStockPrice(ema);
            emaPrice.setDateOfStock(priceList.get(i).getDateOfStock());
            emaList.add(emaPrice);
        }
        return emaList;
    }
}
