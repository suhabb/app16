package com.example.app16.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.app16.R;

import java.util.ArrayList;

/*
View value cannot be plainly accessed. Need an alternative...
 */
class ViewDataHandlers extends Fragment {
    View root;
    Context myContext;
    CheckBox smaBox;
    CheckBox emaBox;
    CheckBox macdBox;
    CheckBox macdavqBox;
    EditText stockSymbol;
    EditText quoteFromDate ;
    EditText quoteEndDate;


    public static analyseFragment newInstance(Context c)
    { analyseFragment fragment = new analyseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.myContext = c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.findquote_layout, container, false);
        Bundle data = getArguments();
        smaBox = root.findViewById(R.id.sma);
        emaBox = root.findViewById(R.id.ema);
        macdBox = root.findViewById(R.id.macd);
        macdavqBox = root.findViewById(R.id.avg);
        stockSymbol = root.findViewById(R.id.shareSymbolField);
        quoteFromDate = root.findViewById(R.id.findQuotedateField);
        quoteEndDate = root.findViewById(R.id.findQuoteDateToField);

        return root;
    }

     public ArrayList<CheckBox> getTickedIndicators() {
        ArrayList<CheckBox> checkedBoxes = new ArrayList<>();
        if (smaBox.isChecked()) {
            checkedBoxes.add(smaBox);
        } else if (emaBox.isChecked()) {
            checkedBoxes.add(emaBox);
        } else if (macdBox.isChecked()) {
            checkedBoxes.add(macdBox);
        } else if (macdavqBox.isChecked()) {
            checkedBoxes.add(macdavqBox);
        }
        return checkedBoxes;
    }

    public String getComposedFileName(){
        return String.format("%s_%s_%s",stockSymbol, quoteFromDate,quoteEndDate);
    }

}
