package com.example.app16.ui.main; 

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.StringTokenizer;
import java.util.Date; 
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 

/*
Does the handling of data conversions in the format YYYY-MM-DD
 */

public class DateComponent
{ public static long getEpochSeconds(String date)
  { DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    try
	{ Date d = df.parse(date); 
      long time = d.getTime()/1000; 
	  return time;
	} catch (Exception _e) { return -1; } 
  }

  public static long getEpochMilliseconds(String format, String date)
  { DateFormat df = new SimpleDateFormat(format); 
    try
	{ Date d = df.parse(date); 
      long time = d.getTime(); 
	  return time;
	} catch (Exception _e) { return -1; } 
  }

  public static long getTime()
  { Date d = new Date(); 
    return d.getTime(); 
  }

  /*
  This method checks if the given range is between 2 year range.
  This is checked by checking if the range is grater than the n of seconds in 2years
   */
  public boolean checkDateRangeInEpoch(String from, String to){

      return getEpochSeconds(to) - getEpochSeconds(from) <= 315360000 * 2;

  }


}