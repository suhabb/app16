package com.example.app16.ui.main;
/*
This class is responsible for performing all the required calculations
Methods to add SMA, EMA, MACD, MACDAVG
 */

import java.util.ArrayList;

public class CalculateFormulas {


    public ArrayList<String> calcForInstrument(IndicatorsEnum type, ArrayList data){

        switch (type){
            case SMA:
                return calculateSMA(data);
            case EMA:
                return calculateEMA(data);
            case MACD:
                return calculateMACD(data);
            case MACDAVG:
                return calculateMACDAVG(data);
        }
        return null;
    }

    public ArrayList<String> calculateSMA(ArrayList data){


        return null;
    }
    public ArrayList<String> calculateEMA(ArrayList data){


        return null;
    }
    public ArrayList<String> calculateMACD(ArrayList data){


        return null;
    }
    public ArrayList<String> calculateMACDAVG(ArrayList data){



        return null;
    }

}
