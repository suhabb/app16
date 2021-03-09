package com.example.app16.ui.main;
/*
This class is responsible for performing all the required calculations
Methods to add SMA, EMA, MACD, MACDAVG
 */

import java.util.ArrayList;

public class CalculateFormulas {

    static ArrayList<String> timeFrames;
    static ArrayList<Double> stockValues;

    public CalculateFormulas(ArrayList[] timeFrameAndValues) {
        timeFrames = (ArrayList<String>) timeFrameAndValues[0];
        stockValues = (ArrayList<Double>) timeFrameAndValues[1];
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
        int emaPeriod = (int) (timeFrames.size() * 0.05);
        int count = emaPeriod;
        while (count < timeFrames.size()) {
            lists[0].add(timeFrames.get(count));
            lists[1].add(sumEMAvalues(count-emaPeriod,count));
            count += emaPeriod;
        }
        return lists;
    }

    public ArrayList[] calculateMACD() {


        return null;
    }

    public ArrayList[] calculateMACDAVG() {


        return null;
    }

    //EMA=Price(t)×k+EMA(y)×(1−k)
    //=today
    //y=yesterday
    //N=number of days in EMA
    //k=2÷(N+1)
    //​
    public double sumEMAvalues(int x, int numberOfDays) {
        double sum = 0;
        double k = (double) 2 / (numberOfDays + 1);
        for (int i = x; i < numberOfDays; i++) {
            sum += (stockValues.get(i) * k) + (stockValues.get(i + 1) * (1 - k));
        }
        return (sum);
    }

    public double sumValues(int x, int y) {
        double sum = 0;
        for (int i = x; i < y; i++) {
            sum += stockValues.get(i);
        }
        return (sum);
    }
}
