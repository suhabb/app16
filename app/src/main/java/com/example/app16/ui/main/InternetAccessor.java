package com.example.app16.ui.main;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
Does the handling of HTTP GET requests
 */
public class InternetAccessor extends AsyncTask<String, Void, String> {

    private InternetCallback delegate = null;
    private static InternetAccessor instance = null;

    public void setDelegate(InternetCallback c) {
        delegate = c;
    }

    public static InternetAccessor getInstance() {
        if (instance == null) {
            instance = new InternetAccessor();
        }
        return instance;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String myData = "";
        try {
            myData = fetchUrl(url);
        } catch (Exception _e) {
            delegate.internetAccessCompleted(myData);
            return null;
        }
        return myData;
    }

    private String fetchUrl(String url) {
        String urlContent = "";
        StringBuilder myStrBuff = new StringBuilder();
        try {
            URL myUrl = new URL(url);
            HttpURLConnection myConn = (HttpURLConnection) myUrl.openConnection();
            myConn.setRequestProperty("User-Agent", "");
            myConn.setRequestProperty("x-rapidapi-key", "4e47937527mshf111bc4e02a3f6fp1ce684jsn6de2467b8246");
            myConn.setRequestProperty("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
            myConn.setRequestMethod("GET");
            myConn.setDoInput(true);

            myConn.connect();
            myConn.setReadTimeout(5000); //Due to increased latency (because of free API), this is a must...

            InputStream myInStrm = myConn.getInputStream();
            BufferedReader myBuffRdr = new BufferedReader(new InputStreamReader(myInStrm));

            while ((urlContent = myBuffRdr.readLine()) != null) {
                myStrBuff.append(urlContent + '\n');
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            delegate.internetAccessCompleted(myStrBuff.toString());
            return null;
        }
        return myStrBuff.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.internetAccessCompleted(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

interface InternetCallback {
    public void internetAccessCompleted(String response);
}


