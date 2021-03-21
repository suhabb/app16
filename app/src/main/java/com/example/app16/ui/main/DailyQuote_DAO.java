package com.example.app16.ui.main;


//Handles all the quote values and parsing and conversions
public class DailyQuote_DAO {
    public static final String baseString = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?";

    /*
    This method will replace the method below to format url
    @input: symbol name, date series in epochs (only passed date range validation)
    Sample string->
    https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-chart?interval=1d
    &symbol=TSLA&region=US&period1=1514117309&period2=1614318309
    */
    public static String formatUrlString(String stockSymbol, long fromEpoch, long toEpoch) {
        String formatUrl = "" + baseString + "interval=1d&symbol="; //trailing & to connect next param
        try {
            if (!stockSymbol.equals("") & stockSymbol != null) {
                formatUrl += stockSymbol + "&";
            }
            formatUrl += "region=US&";
            if (fromEpoch != 0 && toEpoch != 0) {
                formatUrl += "period1=" + fromEpoch + "&period2=" + toEpoch;
            }
            System.out.println("Calling Url....:" + formatUrl);
            return formatUrl;

        } catch (Exception e) {
            System.out.println("Error formatting url. It's likely due to a wrong symbol name.");
        }
        return "MalformedURL";
    }

}