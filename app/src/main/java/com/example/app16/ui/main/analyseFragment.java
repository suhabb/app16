package com.example.app16.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.app16.R;


//Handles the analyse fragment screen
public class analyseFragment extends Fragment implements OnClickListener
{ View root;
  Context myContext;
  analyseBean analysebean;

  ImageView analyseResult;
  ImageView analyseResult2;
  Button analyseOkButton;
  Button analysecancelButton;
  TextView indicResult;
  TextView indicResult2;

 public analyseFragment() {}

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
  { root = inflater.inflate(R.layout.analyse_layout, container, false);
    Bundle data = getArguments();
    analyseResult = (ImageView) root.findViewById(R.id.analyseResult);
    analyseResult2 = (ImageView) root.findViewById(R.id.analyseResult2);
    analysebean = new analyseBean(myContext);
    analyseOkButton = root.findViewById(R.id.analyseOK);
    analyseOkButton.setOnClickListener(this);
    analysecancelButton = root.findViewById(R.id.analyseCancel);
    indicResult =  root.findViewById(R.id.ResultLabel);
    indicResult2 =  root.findViewById(R.id.ResultLabel2);
    analysecancelButton.setOnClickListener(this);

    return root;
  }

  public void onClick(View _v)
  { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
    try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
    if (_v.getId() == R.id.analyseOK)
    { analyseOK(_v); }
    else if (_v.getId() == R.id.analyseCancel)
    { analyseCancel(_v); }
  }

  // Invocation of graph calls here
  public void analyseOK(View _v) 
  { 
    if (analysebean.isanalyseerror())
    { Log.w(getClass().getName(), analysebean.errors());
      Toast.makeText(myContext, "Errors: " + analysebean.errors(), Toast.LENGTH_LONG).show();
    }
    else
    {
      //Get the ticked checkboxes and then iterative call to get the results
      int ctr = 0;
      for (Object cBoxes : findQuoteFragment.checkedBoxes){
          GraphDisplay _result = analysebean.analyse(cBoxes.toString());
          if (ctr == 0) {
              indicResult.setText(IndicatorsEnum.resolveType(cBoxes.toString()).name());
              analyseResult.setImageDrawable(_result);
          } else{
              indicResult2.setText(IndicatorsEnum.resolveType(cBoxes.toString()).name());
              analyseResult2.setImageDrawable(_result);
          }
          ctr+=1;
      }
    }
  }


  public void analyseCancel(View _v)
  { analysebean.resetData();
  }
}
