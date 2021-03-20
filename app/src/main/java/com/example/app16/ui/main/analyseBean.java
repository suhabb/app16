package com.example.app16.ui.main;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


//Encapsulates the required class objects
public class analyseBean {

    ModelFacade model = null;
    private List errors = new ArrayList();

    public analyseBean(Context _c) {
        model = ModelFacade.getInstance(_c);
    }

    public void resetData() {
    }

    public boolean isanalyseerror() {
        errors.clear();
        return errors.size() > 0;
    }

    public String errors() {
        return errors.toString();
    }

    public GraphDisplay analyse(String cboxes) {
        try {
            return model.analyse(ModelFacade.fileName, cboxes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GraphDisplay();
    }
}

