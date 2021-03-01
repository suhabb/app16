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
    String formatUrl = ""+baseString+"interval1d&"; //trailing & to connect next param
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



  public static String getURL(String command, ArrayList<String> pars, ArrayList<String> values)
  { String res = "https://query1.finance.yahoo.com/v7/finance/download/";

    if (command != null)
    { res = res + command; }
    if (pars.size() == 0)
    { return res; }
    res = res + "?";
    for (int i = 0; i < pars.size(); i++)
    { String par = pars.get(i); 
      String val = values.get(i); 
      res = res + par + "=" + val;
      if (i < pars.size() - 1)
      { res = res + "&"; }
    }
    return res;
  }

  public static boolean isCached(String id)
  { DailyQuote _x = DailyQuote.DailyQuote_index.get(id);
    if (_x == null) { return false; }
    return true;
  }



  public static DailyQuote getCachedInstance(String id)
  { return DailyQuote.DailyQuote_index.get(id); }


  //to change this, so that it works to the current requirement.
  public static DailyQuote parseCSV(String _line)
  { if (_line == null) { return null; }
    ArrayList<String> _line1vals = Ocl.tokeniseCSV(_line);
    DailyQuote dailyquotex;
    dailyquotex = DailyQuote.DailyQuote_index.get((String) _line1vals.get(0));
    if (dailyquotex == null) {
    dailyquotex = DailyQuote.createByPKDailyQuote((String) _line1vals.get(0));
    }
    dailyquotex.date = (String) _line1vals.get(0);
    dailyquotex.open = Double.parseDouble((String) _line1vals.get(1));
    dailyquotex.high = Double.parseDouble((String) _line1vals.get(2));
    dailyquotex.low = Double.parseDouble((String) _line1vals.get(3));
    dailyquotex.close = Double.parseDouble((String) _line1vals.get(4));
    dailyquotex.adjclose = Double.parseDouble((String) _line1vals.get(5));
    dailyquotex.volume = Double.parseDouble((String) _line1vals.get(6));
    return dailyquotex;
  }


  //to change this, so that it works to the current requirement.
  public static DailyQuote parseJSON(JSONObject obj)
  { if (obj == null) { return null; }

    try {
      String date = obj.getString("date");
      DailyQuote _dailyquotex = DailyQuote.DailyQuote_index.get(date);
      if (_dailyquotex == null) {
      _dailyquotex = DailyQuote.createByPKDailyQuote(date);
      }
      _dailyquotex.date = obj.getString("date");
      _dailyquotex.open = obj.getDouble("open");
      _dailyquotex.high = obj.getDouble("high");
      _dailyquotex.low = obj.getDouble("low");
      _dailyquotex.close = obj.getDouble("close");
      _dailyquotex.adjclose = obj.getDouble("adjclose");
      _dailyquotex.volume = obj.getDouble("volume");
      return _dailyquotex;
    } catch (Exception _e) { return null; }
  }



  public static ArrayList<DailyQuote> makeFromCSV(String lines)
  { ArrayList<DailyQuote> result = new ArrayList<DailyQuote>();

    if (lines == null)
    { return result; }

    ArrayList<String> rows = Ocl.parseCSVtable(lines);

    for (int i = 1; i < rows.size(); i++)
    { String row = rows.get(i);
      if (row == null || row.trim().length() == 0)
      { }
      else
      { DailyQuote _x = parseCSV(row);
        if (_x != null)
        { result.add(_x); }
      }
    }
    return result;
  }



  public static ArrayList<DailyQuote> parseJSON(JSONArray jarray)
  { if (jarray == null) { return null; }
    ArrayList<DailyQuote> res = new ArrayList<DailyQuote>();

    int len = jarray.length();
    for (int i = 0; i < len; i++)
    { try { JSONObject _x = jarray.getJSONObject(i);
        if (_x != null)
        { DailyQuote _y = parseJSON(_x); 
          if (_y != null) { res.add(_y); }
        }
      }
      catch (Exception _e) { }
    }
    return res;
  }



  public static JSONObject writeJSON(DailyQuote _x)
  { JSONObject result = new JSONObject();
    try {
       result.put("date", _x.date);
       result.put("open", _x.open);
       result.put("high", _x.high);
       result.put("low", _x.low);
       result.put("close", _x.close);
       result.put("adjclose", _x.adjclose);
       result.put("volume", _x.volume);
      } catch (Exception _e) { return null; }
    return result;
  }



  public static JSONArray writeJSONArray(ArrayList<DailyQuote> es)
  { JSONArray result = new JSONArray();
    for (int _i = 0; _i < es.size(); _i++)
    { DailyQuote _ex = es.get(_i);
      JSONObject _jx = writeJSON(_ex);
      if (_jx == null) { } 
      else 
      { try { result.put(_jx); }
        catch (Exception _ee) { }
      }
    }
    return result;
  }


}
