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

  public GraphDisplay analyse()
  {
    System.out.println(viewData.getComposedFileName() + "   127 " );
    try {
      return model.analyse(viewData.getComposedFileName(),viewData.getTickedIndicators());  // cannot fetch values from another view
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    //return new GraphDisplay();
  }
}

