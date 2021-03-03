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
                break;
            case EMA:
                return calculateEMA(data);
                break;
            case MACD:
                return calculateMACD(data);
                break;
            case MACDAVG:
                return calculateMACDAVG(data);
                break;
        }
    }

    public ArrayList<String> calculateSMA(ArrayList data){



    }
    public ArrayList<String> calculateEMA(ArrayList data){



    }
    public ArrayList<String> calculateMACD(ArrayList data){



    }
    public ArrayList<String> calculateMACDAVG(ArrayList data){




    }

}
