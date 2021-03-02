package com.example.app16.ui.main;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


//To create, read and write files. Maybe can be used for the persistence problem too
public class FileAccessor
{
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

}
