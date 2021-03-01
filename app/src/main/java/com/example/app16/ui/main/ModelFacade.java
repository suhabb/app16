package com.example.app16.ui.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

//implemented by mainActivity as a modelling instance
//defines the model and click calls come from the top (as in the uml files)
public class ModelFacade
  implements InternetCallback {

    FileAccessor fileSystem;
    Context myContext;
    static ModelFacade instance = null;
    AssetManager manager = null;
    CacheComponent cacheComponent;

    public static ModelFacade getInstance(Context context) {
        if (instance == null) {
            instance = new ModelFacade(context);
        }
        return instance;
    }


    private ModelFacade(Context context) {
        myContext = context;
        fileSystem = new FileAccessor(context);
        cacheComponent = new CacheComponent(context);
    }

    // file saving takes place here upon successful GET request
    public void internetAccessCompleted(String response) {
        DailyQuote_DAO.makeFromCSV(response);

    }

    public String findQuote(String date) {
        String result = "";
        if (DailyQuote_DAO.isCached(date)) {
            result = "Data already exists";
            return result;
        }
        long t1 = 0;
        t1 = DateComponent.getEpochSeconds(date);
        long t2 = 0;
        t2 = (t1 + 7 * 86400);
        String url = "";
        ArrayList<String> sq1 = null;
        sq1 = Ocl.copySequence(Ocl.initialiseSequence("period1", "period2", "interval", "events"));
        ArrayList<String> sq2 = null;
        sq2 = Ocl.copySequence(Ocl.initialiseSequence((t1 + ""), (t2 + ""), "1d", "history"));
        url = DailyQuote_DAO.getURL("GBPUSD=X", sq1, sq2);
        InternetAccessor x = null;
        x = new InternetAccessor();
        x.setDelegate(this);
        x.execute(url);
        result = ("Called url: " + url);
        return result;
    }

        //method will replace the above method
        // @ intake: symbol, from and to date. Check date range is another thing
      @RequiresApi(api = Build.VERSION_CODES.O)
      public String findStockQuote(String shareSymbol, String fromDate, String toDate){
        //String fileName = String.format("%s-%s-%s",shareSymbol,fromDate,toDate);
        String respResult = "";
        //check for cached outcome
        if (cacheComponent.getFilenameOfStock(shareSymbol, fromDate, toDate)){
          return "Data already cached. No further requests made.";

        }else{
          String url = DailyQuote_DAO.formatUrlString(shareSymbol,DateComponent.getEpochSeconds(fromDate),DateComponent.getEpochSeconds(toDate));
          InternetAccessor getCaller = new InternetAccessor();
          getCaller.setDelegate(this);
          getCaller.execute(url);
        }
        return "Get request successful!";
      }

      //graph call action
      public GraphDisplay analyse()
      {
        GraphDisplay result = null;
        result = new GraphDisplay();
        ArrayList<DailyQuote> quotes = null;
        quotes = Ocl.copySequence(DailyQuote.DailyQuote_allInstances);
        ArrayList<String> xnames = null;
        xnames = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.date;}));
        ArrayList<Double> yvalues = null;
        yvalues = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.close;}));
        result.setXNominal(xnames);
        result.setYPoints(yvalues);

        return result;
        }

}
