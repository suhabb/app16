package com.example.app16;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
}