package com.example.app16.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//implements the findQuoteFragment class
public class findQuoteBean {

    ModelFacade model = null;
    private String dateFrom = "";
    private String stockSymbol = "";
    private String dateTo = "";
    private List<String> errors = new ArrayList();

    public findQuoteBean(Context _c) {
        model = ModelFacade.getInstance(_c);
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void resetData() {
        dateFrom = "";
        stockSymbol = "";
        dateTo = "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isfindQuoteerror() {
        errors.clear();
        if (dateFrom.isEmpty() || dateTo.isEmpty()) {
            errors.add("Date cannot be empty");
        }
        if (stockSymbol.isEmpty()) {
            errors.add("Stock Id cannot be empty");
        }
        if (!dateFrom.isEmpty() && !dateTo.isEmpty()) {
            if (!validateDate(dateFrom, dateTo)) {
                errors.add("Date not in proper format");
                return errors.size() > 0;
            }
            if (!validateDateRange(dateFrom, dateTo)) {
                errors.add("Start Date must be less then End Date");
            }
            if (minDateRangeRequired(dateFrom, dateTo)) {
                errors.add("Minimum 60 days of gap between From and To date expected.");
            }
        }
        return errors.size() > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean minDateRangeRequired(String dateFrom, String dateTo) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromDate = LocalDate.parse(dateFrom, dtf);
            LocalDate toDate = LocalDate.parse(dateTo, dtf);
            long gap = ChronoUnit.DAYS.between(fromDate, toDate);
            if (gap < 60) {
                return true;
            }
            return false;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateDate(String dateFrom, String dateTo) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateFrom, dtf);
            LocalDate.parse(dateTo, dtf);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateDateRange(String dateFrom, String dateTo) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromDate = LocalDate.parse(dateFrom, dtf);
            LocalDate toDate = LocalDate.parse(dateTo, dtf);
            if(fromDate.isAfter(toDate)){
                return false;
            }
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public String errors() {
        return errors.stream().collect(Collectors.joining(", "));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String findQuote(String shareSymbol, String fromDate, String toDate, int interval) {
        return model.findStockQuote(shareSymbol, fromDate, toDate, interval);
    }

}

