package com.example.app16.ui.main;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.app16.R;

import java.util.ArrayList;


//controller for handling the quote request
public class findQuoteFragment extends Fragment implements OnClickListener {

    View root;
    Context myContext;
    findQuoteBean findquotebean;
    static int checkBoxCount;
    static ArrayList<CheckBox> checkedBoxes = new ArrayList<>();
    EditText findQuotedateTextField;
    String findQuotedateData;
    EditText stockSymbol;
    EditText quoteFromDate;
    EditText quoteEndDate;
    TextView findQuoteResult;
    Button findQuoteOkButton;
    Button findQuotecancelButton;
    CheckBox smaBox;
    CheckBox emaBox;
    CheckBox macdBox;
    CheckBox macdavqBox;
    int progress;
    TextView tvProgressLabel;
    SeekBar seekBar;

    public findQuoteFragment() {
    }

    public static findQuoteFragment newInstance(Context c) {
        findQuoteFragment fragment = new findQuoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.myContext = c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.findquote_layout, container, false);
        Bundle data = getArguments();
        findQuotedateTextField = root.findViewById(R.id.findQuotedateField);
        stockSymbol = root.findViewById(R.id.shareSymbolField);
        stockSymbol.setText("TSLA");
        quoteFromDate = root.findViewById(R.id.findQuotedateField);
        quoteFromDate.setText("2016-06-11");
        quoteEndDate = root.findViewById(R.id.findQuoteDateToField);
        quoteEndDate.setText("2017-08-11");
        findQuoteResult = (TextView) root.findViewById(R.id.findQuoteResult);
        findquotebean = new findQuoteBean(myContext);
        findQuoteOkButton = root.findViewById(R.id.findQuoteOK);
        findQuoteOkButton.setOnClickListener(this);
        findQuotecancelButton = root.findViewById(R.id.findQuoteCancel);
        findQuotecancelButton.setOnClickListener(this);
        smaBox = root.findViewById(R.id.sma);
        smaBox.setOnClickListener(this);
        emaBox = root.findViewById(R.id.ema);
        emaBox.setOnClickListener(this);
        macdBox = root.findViewById(R.id.macd);
        macdBox.setOnClickListener(this);
        macdavqBox = root.findViewById(R.id.avg);
        macdavqBox.setOnClickListener(this);

        // set a change listener on the SeekBar
        seekBar = root.findViewById(R.id.seekBar);
        seekBar.setProgress(5);


        progress = seekBar.getProgress();
        tvProgressLabel = root.findViewById(R.id.textView);
        tvProgressLabel.setText("" + progress);
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

            int progressChangedValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                tvProgressLabel.setText("" + progress);
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //
            }

        };
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        return root;
    }

    // The main click methods are called from here by the view instance
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View _v) {
        InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        try {
            _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0);
        } catch (Exception _e) {
        }
        if (_v.getId() == R.id.findQuoteOK) {
            findQuoteOK(_v);
        } else if (_v.getId() == R.id.findQuoteCancel) {
            findQuoteCancel(_v);
        } else if (_v.getId() == R.id.sma || _v.getId() == R.id.ema || _v.getId() == R.id.macd || _v.getId() == R.id.avg) {
            validateTickedBox(_v);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void findQuoteOK(View _v) {

        findquotebean.setDateFrom(quoteFromDate.getText().toString());
        findquotebean.setDateTo(quoteEndDate.getText().toString());
        findquotebean.setStockSymbol(stockSymbol.getText().toString());
        SeekBar seekBar = root.findViewById(R.id.seekBar);

        if (findquotebean.isfindQuoteerror()) {
            Log.w(getClass().getName(), findquotebean.errors());
            Toast.makeText(myContext, "Errors: " + findquotebean.errors(), Toast.LENGTH_LONG).show();
            findQuoteResult.setText("");
        }  else {
            findQuoteResult.setText(findquotebean.findQuote(stockSymbol.getText().toString(),
                    quoteFromDate.getText().toString(), quoteEndDate.getText().toString(), seekBar.getProgress()));
        }
    }

    public void findQuoteCancel(View _v) {
        findquotebean.resetData();
        findQuotedateTextField.setText("");
        findQuoteResult.setText("");
        stockSymbol.setText("");
        quoteEndDate.setText("");
        stockSymbol.setFocusable(true);
        checkedBoxes.stream().forEach(c -> c.setChecked(false));
        checkedBoxes.clear();
        checkBoxCount = 0;
        seekBar.setProgress(5);
    }

    public void validateTickedBox(View _v) {
        CheckBox cBox = (CheckBox) _v;
        if (cBox.isChecked() & checkBoxCount < 2) {
            checkBoxCount += 1;
            checkedBoxes.add(cBox);
        } else if (!cBox.isChecked()) {
            checkBoxCount -= 1;
            checkedBoxes.remove(cBox);
        } else {
            cBox.setChecked(false);
            findQuoteResult.setText("Please only select 2 indicators at max");
        }
    }
}
