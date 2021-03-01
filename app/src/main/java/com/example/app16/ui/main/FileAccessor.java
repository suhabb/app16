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
{ Context myContext;


    public FileAccessor(Context context) {
        myContext = context;
    }

    public void createFile(String filename) {
        try {
            File newFile = new File(myContext.getFilesDir(), filename);
        } catch (Exception _e) {
            _e.printStackTrace();
        }
    }

    public ArrayList<String> readFile(String filename) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            InputStream inStrm = myContext.openFileInput(filename);
            if (inStrm != null) {
                InputStreamReader inStrmRdr = new InputStreamReader(inStrm);
                BufferedReader buffRdr = new BufferedReader(inStrmRdr);
                String fileContent;

                while ((fileContent = buffRdr.readLine()) != null) {
                    result.add(fileContent);
                }
                inStrm.close();
            }
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return result;
    }

    public void writeFile(String filename, ArrayList<String> contents) {
        try {
            OutputStreamWriter outStrm =
                    new OutputStreamWriter(myContext.openFileOutput(filename, Context.MODE_PRIVATE));
            try {
                for (int i = 0; i < contents.size(); i++) {
                    outStrm.write(contents.get(i) + "\n");
                }
            } catch (IOException _ix) {
            }
            outStrm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
