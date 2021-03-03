package com.example.app16.ui.main;
/*
This class is responsible for performing all the required calculations
Methods to add SMA, EMA, MACD, MACDAVG
 */

import java.util.ArrayList;

public class CalculateFormulas {

    static ArrayList<String> timeFrames;
    static ArrayList<Double> stockValues;
    public CalculateFormulas( ArrayList timeFrameAndValues){
        timeFrames = (ArrayList<String>) timeFrameAndValues.remove(0);
        stockValues = (ArrayList<Double>) timeFrameAndValues.remove(1);
    }

    public ArrayList[] calcForInstrument(IndicatorsEnum type){

        switch (type){
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
    private ArrayList[] calculateSMA(){
        ArrayList<> lists[] = new ArrayList[2];
        for(int i=0;i<2;i++){ lists[i]=new ArrayList<>(); }
        int smaPeriod = (int) (timeFrames.size() * 0.05);
        int count = smaPeriod;
        while (count<timeFrames.size()){
            lists[0].add(timeFrames.get(count));
            lists[1].add(sumValues(count-smaPeriod,count)/smaPeriod);
            count+=smaPeriod;
        }
        return lists;
    }
    public ArrayList[] calculateEMA( ){


        return null;
    }
    public ArrayList[] calculateMACD( ){


        return null;
    }
    public ArrayList[] calculateMACDAVG( ){



        return null;
    }

    public int sumValues(int x, int y)
    {
        double sum = 0;
        for(int i = x; i < y; i++)
        {
            sum += stockValues.get(i);
        }
        return ((int) sum);
    }
}
