package com.example.app16.ui.main;


import java.util.*;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;

//Handles all the quote values and parsing and conversions
public class DailyQuote_DAO
{
  public static final String baseString = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?";

  /*
  this method will replace the method below to format url
  @input: symbol name, date series in epochs (only passed date range validation)
  Sample string->
  https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d
  &symbol=TSLA&region=US&period1=1514117309&period2=1614318309
  */
    public static String formatUrlString(String stockSymbol, long fromEpoch, long toEpoch ){
    String formatUrl = ""+baseString+"interval=1d&symbol="; //trailing & to connect next param
    try{
      if (!stockSymbol.equals("") & stockSymbol != null){
        formatUrl+=stockSymbol+"&";
      }
      formatUrl+="region=US&";
      if (fromEpoch != 0 && toEpoch != 0){
        formatUrl+="period1="+fromEpoch+"&period2="+toEpoch;
      }
      return formatUrl;

    }catch (Exception e){
      System.out.println("Error formatting url. It's likely due to a wrong symbol name.");
    }
    return "MalformedURL";
    }



    public static JSONObject writeJSON(DailyQuote _x) {
        JSONObject result = new JSONObject();
        try {
            result.put("date", _x.date);
            result.put("open", _x.open);
            result.put("high", _x.high);
            result.put("low", _x.low);
            result.put("close", _x.close);
            result.put("adjclose", _x.adjclose);
            result.put("volume", _x.volume);
        } catch (Exception _e) {
            return null;
        }
        return result;
    }


    public static JSONArray writeJSONArray(ArrayList<DailyQuote> es) {
        JSONArray result = new JSONArray();
        for (int _i = 0; _i < es.size(); _i++) {
            DailyQuote _ex = es.get(_i);
            JSONObject _jx = writeJSON(_ex);
            if (_jx == null) {
            } else {
                try {
                    result.put(_jx);
                } catch (Exception _ee) {
                }
            }
        }
        return result;
    }


}
