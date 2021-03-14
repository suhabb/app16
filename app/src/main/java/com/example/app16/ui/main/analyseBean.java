package com.example.app16.ui.main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.List;

import android.content.Context;
import android.widget.CheckBox;

import org.json.simple.parser.ParseException;


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

  public GraphDisplay analyse(String cboxes)
  {
    try {
      return model.analyse(ModelFacade.fileName, cboxes);
    } catch (FileNotFoundException | ParseException e) {
      e.printStackTrace();
    }
    return new GraphDisplay();
  }
}

