package com.example.app16.ui.main;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;
import android.widget.CheckBox;

//Encapsulates the required class objects
public class analyseBean
{ ModelFacade model = null;

  private List errors = new ArrayList();
  private ViewDataHandlers viewData = new ViewDataHandlers();
  public analyseBean(Context _c) { model = ModelFacade.getInstance(_c); }

  public void resetData()
  { }

  public boolean isanalyseerror()
  { errors.clear(); 
    return errors.size() > 0;
  }

  public String errors() { return errors.toString(); }

  public GraphDisplay analyse()
  { return model.analyse(viewData.getComposedFileName(),viewData.getTickedIndicators()); }

}

