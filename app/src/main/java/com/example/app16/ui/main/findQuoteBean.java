package com.example.app16.ui.main;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

//implements the findQuoteFragment class
public class findQuoteBean
{ ModelFacade model = null;

  private String date = "";
  private List errors = new ArrayList();

  public findQuoteBean(Context _c) { model = ModelFacade.getInstance(_c); }

  public void setdate(String datex)
  { date = datex; }

  public void resetData()
  { date = "";
    }

  public boolean isfindQuoteerror()
  { errors.clear(); 
    return errors.size() > 0;
  }

  public String errors() { return errors.toString(); }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public String findQuote(String shareSymbol, String fromDate, String toDate)
  {
    //return model.findQuote(date);
    return model.findStockQuote(shareSymbol, fromDate, toDate);
  }

}

