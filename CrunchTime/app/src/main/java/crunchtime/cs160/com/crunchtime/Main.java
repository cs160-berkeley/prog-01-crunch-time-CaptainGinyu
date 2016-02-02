package crunchtime.cs160.com.crunchtime;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class Main extends AppCompatActivity implements OnItemSelectedListener, OnFocusChangeListener
{
    protected InputMethodManager inputMethodManager;

    protected HashMap<String[], Float> exercisesWithAmounts;

    protected float currInputtedNumber;

    protected EditText numberInputField;
    protected TextWatcher numberInputFieldWatcher;

    protected Spinner modeSelector;
    protected ArrayAdapter<CharSequence> modeSelectorAdapter;
    protected String currSelectedMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        exercisesWithAmounts = new HashMap<String[], Float>();
        exercisesWithAmounts.put(new String[]{"push-ups", "reps"}, 350f);
        exercisesWithAmounts.put(new String[]{"sit-ups", "reps"}, 200f);
        exercisesWithAmounts.put(new String[]{"squats", "reps"}, 225f);
        exercisesWithAmounts.put(new String[]{"leg-lifting", "minutes"}, 25f);
        exercisesWithAmounts.put(new String[]{"planking", "minutes"}, 25f);
        exercisesWithAmounts.put(new String[]{"jumping jacks", "minutes"}, 10f);
        exercisesWithAmounts.put(new String[]{"pull-ups", "reps"}, 100f);
        exercisesWithAmounts.put(new String[]{"cycling", "minutes"}, 12f);
        exercisesWithAmounts.put(new String[]{"walking", "minutes"}, 20f);
        exercisesWithAmounts.put(new String[]{"jogging", "minutes"}, 12f);
        exercisesWithAmounts.put(new String[]{"swimming", "minutes"}, 13f);
        exercisesWithAmounts.put(new String[]{"stair-climbing", "minutes"}, 15f);

        currInputtedNumber = 0;

        numberInputField = (EditText) findViewById(R.id.numberInputField);
        numberInputFieldWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    currInputtedNumber = Float.parseFloat(s.toString());
                }
                catch(Exception e)
                {
                    currInputtedNumber = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                Log.i("NUMBER!!!", Float.toString(currInputtedNumber));
            }
        };

        numberInputField.addTextChangedListener(numberInputFieldWatcher);
        numberInputField.setOnFocusChangeListener(this);

        modeSelector = (Spinner) findViewById(R.id.modeSelector);
        modeSelectorAdapter = ArrayAdapter.createFromResource(this, R.array.modes, R.layout.support_simple_spinner_dropdown_item);
        modeSelector.setAdapter(modeSelectorAdapter);
        modeSelector.setOnItemSelectedListener(this);
        currSelectedMode = modeSelector.getItemAtPosition(0).toString();
        Log.i("SELECTED!!!", currSelectedMode);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        currSelectedMode = parent.getSelectedItem().toString();
        Log.i("SELECTED!!!", currSelectedMode);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if (!hasFocus)
        {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
