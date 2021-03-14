package com.example.app16.ui.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

//import org.json.simple.parser.ParseException;

//implemented by mainActivity as a modelling instance
//defines the model and click calls come from the top (as in the uml files)
public class ModelFacade
        implements InternetCallback {

    FileAccessor fileSystem;
    Context myContext;
    static ModelFacade instance = null;
    AssetManager manager = null;
    CacheComponent cacheComponent;
    static String fileName;
    private int period;

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

    // File saving takes place here upon successful GET request
    public void internetAccessCompleted(String response) {
        fileSystem.createFile(fileName);
        fileSystem.writeFile(fileName, response);
    }

    //method will replace the above method
    // @ intake: symbol, from and to date. Check date range is another thing
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String findStockQuote(String shareSymbol, String fromDate, String toDate,int period) {
        fileName = String.format("%s_%s_%s", shareSymbol, fromDate, toDate);
        this.period=period;
        //check for cached outcome
        if (cacheComponent.getFilenameOfStock(shareSymbol, fromDate, toDate)) {
            return "Data already cached. No further requests made!";

        } else {
            String url = DailyQuote_DAO.formatUrlString(shareSymbol, DateComponent.getEpochSeconds(fromDate),
                    DateComponent.getEpochSeconds(toDate));
            InternetAccessor getCaller = new InternetAccessor();
            getCaller.setDelegate(this);
            getCaller.execute(url);
        }
        return "Get request successful and cached the file!";
    }

    /*
    Add to obtain the files of data, which are the timeframes and values in 2 arraylist
     */
    public GraphDisplay analyse(String filename, String indicators) throws FileNotFoundException, ParseException {
        ArrayList[] timeFrameAndValues = fileSystem.getJsonFileData(fileSystem.readFile(filename));
        CalculateFormulas cF = new CalculateFormulas(timeFrameAndValues,period);
        IndicatorsEnum IndicType = IndicatorsEnum.resolveType(indicators);
        ArrayList[] calculatedValues = cF.calcForInstrument(IndicType);
        return getNewGraphDisplay(calculatedValues);
    }

    public GraphDisplay getNewGraphDisplay(ArrayList[] xyValues) {
        GraphDisplay result = new GraphDisplay();
        System.out.println("84: " + xyValues[0]);
        System.out.println("85: " + xyValues[1]);
        result.setXNominal((ArrayList<String>) xyValues[0]);
        result.setYPoints((ArrayList<Double>) xyValues[1]);
        return (result);

//            ArrayList<DailyQuote> quotes = null;
//            quotes = Ocl.copySequence(DailyQuote.DailyQuote_allInstances);
//            ArrayList<String> xnames = null;
//            xnames = Ocl.copySequence(Ocl.collectSequence(quotes,(q)-> q.date));
//            ArrayList<Double> yvalues = null;
//            yvalues = Ocl.copySequence(Ocl.collectSequence(quotes,(q)-> q.close));
//            result.setXNominal(xnames);
//            result.setYPoints(yvalues);

    }


}
