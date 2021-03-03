package com.example.app16;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException {
        String urlContent = "";
        URL myUrl = new URL("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d&symbol=TSLA&region=US&period1=1514117309&period2=1614318309");
        HttpURLConnection myConn = (HttpURLConnection) myUrl.openConnection();
        myConn.setRequestProperty("User-Agent", "");
        myConn.setRequestProperty("x-rapidapi-key","4e47937527mshf111bc4e02a3f6fp1ce684jsn6de2467b8246");
        myConn.setRequestProperty("x-rapidapi-host","apidojo-yahoo-finance-v1.p.rapidapi.com");
        myConn.setRequestMethod("GET");
        myConn.setDoInput(true);

        myConn.connect();

        InputStream myInStrm = myConn.getInputStream();
        BufferedReader myBuffRdr = new BufferedReader(new InputStreamReader(myInStrm));

            System.out.println(myBuffRdr.readLine());


        assertEquals(4, 2 + 2);
    }

    @Test
    public void readAndParseToJson() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object a = parser.parse(new FileReader("/Users/azky/Desktop/AndroidProject/app16/app/src/main/assets/storage/TSLA_2015-01-01_2017-01-01.json"));
        JSONObject json = (JSONObject) a;
        JSONObject as = (JSONObject) json.get("chart");
        JSONArray ja = (JSONArray) as.get("result");
        JSONObject ja1 = (JSONObject) ja.get(0);
        JSONArray ja2 = (JSONArray) ja1.get("timestamp");

        JSONObject ja3 = (JSONObject) ja1.get("indicators");
        JSONArray ja4 = (JSONArray) ja3.get("quote");
        JSONObject ja5 = (JSONObject) ja4.get(0);
        JSONArray ja6 = (JSONArray) ja5.get("close");
        int i = 0;

        for (Object _ : ja6){
            System.out.println(ja2.get(i) + " " + ja6.get(i));
            i+=1;
        }

        //System.out.println(ja2);

//        for (Object o : a)
//        {
//            JSONObject person = (JSONObject) o;
//
//            String name = (String) person.get("meta");
//            System.out.println(name);
//
//            for (Object c : name)
//            {
//                System.out.println(c+"");
//            }
//        }


        assertEquals(4, 2 + 2);
    }
}