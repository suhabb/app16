package com.example.app16.ui.main;
/*
This class is responsible for performing all the required calculations
Methods to add SMA, EMA, MACD, MACDAVG
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateFormulas {

    static ArrayList<String> timeFrames;
    static ArrayList<Double> stockValues;
    static ArrayList<Price> priceDateList;

    public CalculateFormulas() {

    }

    public CalculateFormulas(ArrayList[] timeFrameAndValues) {
        timeFrames = (ArrayList<String>) timeFrameAndValues[0];
        stockValues = (ArrayList<Double>) timeFrameAndValues[1];
        priceDateList = (ArrayList<Price>) timeFrameAndValues[2];
    }

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

    /*SMA calculation
    note: due to varying input range, Sma figures are based on a period of 5% of the timeframe
     */
    private ArrayList[] calculateSMA() {
        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }
        int smaPeriod = (int) (timeFrames.size() * 0.05);
        int count = smaPeriod;
        while (count < timeFrames.size()) {
            lists[0].add(timeFrames.get(count));// prev : timeFrames.get(count)
            lists[1].add(sumValues(count - smaPeriod, count) / smaPeriod);
            count += smaPeriod;
        }
        return lists;
    }
    // EMA = (today’s closing price *K) + (Previous EMA * (1 – K))

    //K (Smoothing Factor) = 2/(N+1)
    public ArrayList[] calculateEMA() {
        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }

        List<Price> emaList = getEMAValues(priceDateList, 20);
        List<LocalDate> dateList = emaList
                .stream()
                .map(Price::getDateOfStock)
                .collect(Collectors.toList());
        List<Double> stockPriceList = emaList.stream()
                .map(Price::getStockPrice)
                .collect(Collectors.toList());

        List<LocalDate> xAxisTimeList = dateList.stream().limit(15).collect(Collectors.toList());
        List<Double> yAxisStockList = stockPriceList.stream().limit(15).collect(Collectors.toList());
        lists[0] = new ArrayList(xAxisTimeList);
        lists[1] = new ArrayList(yAxisStockList);
        return lists;

    }

    // MACD=12-Period
    // EMA − 26-Period EMA
    public ArrayList[] calculateMACD() {

        List<Price> macdList = getMACDValues();

        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }

        List<LocalDate> dateList = macdList
                .stream()
                .map(Price::getDateOfStock)
                .collect(Collectors.toList());
        List<Double> stockPriceList = macdList.stream()
                .map(Price::getStockPrice)
                .collect(Collectors.toList());

        List<LocalDate> xAxisTimeList = dateList.stream().limit(15).collect(Collectors.toList());
        List<Double> yAxisStockList = stockPriceList.stream().limit(15).collect(Collectors.toList());
        lists[0] = new ArrayList(xAxisTimeList);
        lists[1] = new ArrayList(yAxisStockList);
        return lists;
    }

    public ArrayList[] calculateMACDAVG() {
        List<Price> macdValues = getMACDValues();
        List<Price> macdAvgList = getEMAValues(macdValues, 9);
        ArrayList lists[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
        }

        List<LocalDate> dateList = macdAvgList
                .stream()
                .map(Price::getDateOfStock)
                .collect(Collectors.toList());
        List<Double> stockPriceList = macdAvgList.stream()
                .map(Price::getStockPrice)
                .collect(Collectors.toList());

        List<LocalDate> xAxisTimeList = dateList.stream().limit(15).collect(Collectors.toList());
        List<Double> yAxisStockList = stockPriceList.stream().limit(15).collect(Collectors.toList());
        lists[0] = new ArrayList(xAxisTimeList);
        lists[1] = new ArrayList(yAxisStockList);
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

    public List<Double> sumEMAvalues2(int period) {

        List<Double> stockList = new ArrayList<>(stockValues);
        Collections.reverse(stockList);
        double k = (double) 2 / (period + 1);
        k = Math.round(k * 100) / 100.0d;
        List<Double> emaList = new ArrayList<>();
        double smaSum = stockList
                .subList(stockList.size() - period - 1, stockList.size() - 1)
                .stream()
                .reduce(0.0, Double::sum) / period;
        double ema = 0.0;
        for (int index = period + 1; index < stockList.size(); index++) {

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
