package com.example.app16.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//To create, read and write files. Maybe can be used for the persistence problem too
public class FileAccessor {
    Context myContext;


    public FileAccessor(Context context) {
        myContext = context;
    }

    public void createFile(String filename) {
        try {
            File newFile = new File(myContext.getFilesDir(), filename);//the file is saved based on the return of getFilesDir().
        } catch (Exception _e) {
            _e.printStackTrace();
        }
    }

    public String readFile(String filename) {
        StringBuilder result = new StringBuilder();

        try {
            InputStream inStrm = myContext.openFileInput(filename);
            if (inStrm != null) {
                InputStreamReader inStrmRdr = new InputStreamReader(inStrm);
                BufferedReader buffRdr = new BufferedReader(inStrmRdr);
                String fileContent;

                while ((fileContent = buffRdr.readLine()) != null) {
                    result.append(fileContent);
                }
                inStrm.close();
            }
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return result.toString();
    }

    public void writeFile(String filename, String contents) {
        try {
            OutputStreamWriter outStrm = new OutputStreamWriter(myContext.openFileOutput(filename, Context.MODE_PRIVATE));
            try {
                outStrm.write(contents + "\n");
            } catch (IOException _ix) {
                System.out.println("Error writing your file : " + _ix);
            }
            outStrm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList[] getJsonFileData(String data) throws ParseException {

        // ArrayList[] tFrameAndValues = new ArrayList[2];//len of 2
        ArrayList tFrameAndValues[] = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            tFrameAndValues[i] = new ArrayList<>();
        }

        JSONParser parser = new JSONParser();
        Object a = parser.parse(data);
        JSONObject json = (JSONObject) a;
        JSONObject as = (JSONObject) json.get("chart");
        JSONArray ja = (JSONArray) as.get("result");
        JSONObject ja1 = (JSONObject) ja.get(0);
        JSONArray ja2 = (JSONArray) ja1.get("timestamp");
        JSONObject ja3 = (JSONObject) ja1.get("indicators");
        JSONArray ja4 = (JSONArray) ja3.get("quote");
        JSONObject ja5 = (JSONObject) ja4.get(0);
        ArrayList<Double> ja6 = (ArrayList<Double>) ja5.get("close");
        //tFrameAndValues[0] = ja2;
        List<Price> priceList = new ArrayList<>();

        for (int i = 0; i < ja2.size(); i++) {
            Long value = (Long) ja2.get(i);
            Date curDate = new Date(value * 10000);
            tFrameAndValues[0].add(curDate.toString().substring(4, 10));
            System.out.println("Current Date:" + curDate.toString());
        }
        tFrameAndValues[1] = ja6;

        for (int i = 0; i < ja2.size(); i++) {
            Long value = (Long) ja2.get(i);
            LocalDate date = Instant.ofEpochSecond(value).atZone(ZoneId.systemDefault()).toLocalDate();
            Price price = new Price();
            price.setDateOfStock(date);
            priceList.add(price);
        }

        for (int i = 0; i < ja6.size(); i++) {
            priceList.get(i).setStockPrice(ja6.get(i));
        }
        tFrameAndValues[2]=new ArrayList(priceList);

        return tFrameAndValues;
    }

}
