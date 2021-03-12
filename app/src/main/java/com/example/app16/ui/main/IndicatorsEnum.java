package com.example.app16.ui.main;

/*
Possible indicators that can be used. More can be added if necessary...
 */
public enum IndicatorsEnum {
    SMA,
    EMA,
    MACD,
    MACDAVG;

    public static IndicatorsEnum resolveType(String strObj){
        IndicatorsEnum type = null;
        if (strObj.toLowerCase().contains("sma")){
            type = IndicatorsEnum.SMA;
        }else if (strObj.toLowerCase().contains("ema")){
            type = IndicatorsEnum.EMA;
        }else if (strObj.toLowerCase().contains("macd")){
            type = IndicatorsEnum.MACD;
        }else if (strObj.toLowerCase().contains("avg")){
            type = IndicatorsEnum.MACDAVG;
        }
        return type;
    }

}
