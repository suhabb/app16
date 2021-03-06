package com.example.app16.ui.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CacheComponent {

    Context myContext;
    AssetManager assetManager;

    //I have altered the return type to Boolean
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean getFilenameOfStock(String tickerId, String fromDateString, String toDateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(fromDateString, formatter);
        LocalDate toDate = LocalDate.parse(toDateString, formatter);
        String[] listFiles = getFileList();
        List<String> storageFileNames = new ArrayList<>();
        if (listFiles.length > 0) {
            Arrays.stream(listFiles).forEach(filename ->
            {
                String[] file = filename.split("_");
                String fileTickerId= file[0];
                String fileFromDate = file[1];
                String fileToDate = file[2];
                if (fileTickerId.equalsIgnoreCase(tickerId)) {
                    LocalDate storageFromDate = LocalDate.parse(fileFromDate, formatter);
                    LocalDate storageToDate = LocalDate.parse(fileToDate, formatter);
                    if ((fromDate.equals(storageFromDate) || fromDate.isAfter(storageFromDate))
                            && (toDate.equals(storageToDate) || toDate.isBefore(storageToDate))) {
                        storageFileNames.add(filename);
                    }
                }
            });
        }
        if(storageFileNames.isEmpty()){
            return false;
        }
        return true;
    }

    public CacheComponent(Context context) {
        this.myContext = context;
    }

    public String[] getFileList() {
        String[] fileNameList = {};
        try {
            assetManager = this.myContext.getAssets();
            fileNameList = assetManager.list("storage");
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return fileNameList;
    }


}
