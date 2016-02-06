package crunchtime.cs160.com.crunchtime;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class Main extends AppCompatActivity implements OnItemSelectedListener, OnFocusChangeListener
{
    public static final float BASE_CALORIES = 100;

    protected InputMethodManager inputMethodManager;

    protected HashMap<String, Float> exercisesWithAmountsNoUnits;
    protected  HashMap<String, String> exerciseToUnits;

    protected float currInputtedNumber;

    protected EditText numberInputField;
    protected TextWatcher numberInputFieldWatcher;

    protected TextView unitsText;

    protected Spinner modeSelector;
    protected ArrayAdapter<CharSequence> modeSelectorAdapter;
    protected String currSelectedMode;

    protected Button convertButton;
    protected TextView exerciseSelectPrompt;
    protected Spinner exerciseSelect;
    protected ArrayAdapter<CharSequence> exerciseSelectAdapter;

    protected float caloriesBurned;
    protected String selectedExercise;

    protected TextView resultTextView;

    private boolean waiting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        waiting = false;
        resultTextView = (TextView) findViewById(R.id.results);

        exercisesWithAmountsNoUnits = new HashMap<String, Float>();
        exercisesWithAmountsNoUnits.put("push-ups", 350f);
        exercisesWithAmountsNoUnits.put("sit-ups", 200f);
        exercisesWithAmountsNoUnits.put("squats", 225f);
        exercisesWithAmountsNoUnits.put("leg-lifting", 25f);
        exercisesWithAmountsNoUnits.put("planking", 25f);
        exercisesWithAmountsNoUnits.put("jumping jacks", 10f);
        exercisesWithAmountsNoUnits.put("pull-ups", 100f);
        exercisesWithAmountsNoUnits.put("cycling", 12f);
        exercisesWithAmountsNoUnits.put("walking", 20f);
        exercisesWithAmountsNoUnits.put("jogging", 12f);
        exercisesWithAmountsNoUnits.put("swimming", 13f);
        exercisesWithAmountsNoUnits.put("stair-climbing", 15f);

        exerciseToUnits = new HashMap<String, String>();
        exerciseToUnits.put("push-ups", "reps");
        exerciseToUnits.put("sit-ups", "reps");
        exerciseToUnits.put("squats", "reps");
        exerciseToUnits.put("leg-lifting", "minutes");
        exerciseToUnits.put("planking", "minutes");
        exerciseToUnits.put("jumping jacks", "minutes");
        exerciseToUnits.put("pull-ups", "reps");
        exerciseToUnits.put("cycling", "minutes");
        exerciseToUnits.put("walking", "minutes");
        exerciseToUnits.put("jogging", "minutes");
        exerciseToUnits.put("swimming", "minutes");
        exerciseToUnits.put("stair-climbing", "minutes");

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
                if (!waiting)
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

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                Log.i("NUMBER!!!", Float.toString(currInputtedNumber));
            }
        };

        numberInputField.addTextChangedListener(numberInputFieldWatcher);
        numberInputField.setOnFocusChangeListener(this);

        unitsText = (TextView) findViewById(R.id.unitsText);

        modeSelector = (Spinner) findViewById(R.id.modeSelector);
        modeSelectorAdapter = ArrayAdapter.createFromResource(this, R.array.modes,
                R.layout.support_simple_spinner_dropdown_item);
        modeSelector.setAdapter(modeSelectorAdapter);
        modeSelector.setOnItemSelectedListener(this);
        currSelectedMode = modeSelector.getItemAtPosition(0).toString();
        Log.i("SELECTED!!!", currSelectedMode);

        convertButton = (Button) findViewById(R.id.convertButton);
        exerciseSelectPrompt = (TextView) findViewById((R.id.exerciseSelectPrompt));
        exerciseSelect = (Spinner) findViewById(R.id.exerciseSelect);
        exerciseSelectAdapter = ArrayAdapter.createFromResource(this, R.array.exercises,
                R.layout.support_simple_spinner_dropdown_item);
        exerciseSelect.setAdapter(exerciseSelectAdapter);
        exerciseSelect.setOnItemSelectedListener(this);
        exerciseSelectPrompt.setVisibility(View.VISIBLE);
        exerciseSelect.setVisibility(View.VISIBLE);
        selectedExercise = exerciseSelect.getItemAtPosition(0).toString();
        Log.i("SELECTED EXERCISE!!!", selectedExercise);
        convertButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!waiting)
                {
                    waiting = true;
                    Log.i("CONVERTING!", "doing conversion!!!");
                    String resultText = "";
                    float result = currInputtedNumber * BASE_CALORIES / exercisesWithAmountsNoUnits.get(selectedExercise);

                    if (currSelectedMode.equals(modeSelector.getItemAtPosition(0)))
                    {
                        resultText += "Doing " + selectedExercise
                                + " for " + currInputtedNumber + " "
                                + exerciseToUnits.get(selectedExercise)
                                + " made you burn "
                                + result + " calories!\n\n";

                        for (HashMap.Entry<String, Float> entry : exercisesWithAmountsNoUnits.entrySet())
                        {
                            if (!entry.getKey().equals(selectedExercise))
                            {
                                resultText += "Equivalent to doing " + entry.getKey() + " for "
                                        + (result * entry.getValue() / BASE_CALORIES)
                                        + " " + exerciseToUnits.get(entry.getKey()) + "\n\n";
                            }
                        }
                    }
                    else if (currSelectedMode.equals(modeSelector.getItemAtPosition(1)))
                    {
                        resultText += "In order to burn " + currInputtedNumber + " calories, you can do either of the following:\n\n";
                        for (HashMap.Entry<String, Float> entry : exercisesWithAmountsNoUnits.entrySet())
                        {
                            resultText += (currInputtedNumber * entry.getValue() / BASE_CALORIES)
                                    + " " + exerciseToUnits.get(entry.getKey()) + " of " + entry.getKey() + "\n\n";
                        }
                    }

                    resultTextView.setText(resultText);
                }
                waiting = false;
            }
        });

        caloriesBurned = 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (parent.getId() == R.id.modeSelector)
        {
            currSelectedMode = parent.getSelectedItem().toString();
            Log.i("SELECTED!!!", currSelectedMode);
            if (currSelectedMode.equals(modeSelector.getItemAtPosition(0)))
            {
                exerciseSelectPrompt.setVisibility(View.VISIBLE);
                exerciseSelect.setVisibility(View.VISIBLE);
                Log.i("SELECTED EXERCISE!!!", selectedExercise);
                for (HashMap.Entry<String, Float> entry : exercisesWithAmountsNoUnits.entrySet())
                {
                    String key = entry.getKey();

                    if (key.equals(selectedExercise))
                    {
                        unitsText.setText(exerciseToUnits.get(key));
                        break;
                    }
                }
            }
            else if (currSelectedMode.equals(modeSelector.getItemAtPosition(1)))
            {
                exerciseSelectPrompt.setVisibility(View.GONE);
                exerciseSelect.setVisibility(View.GONE);
                resultTextView.setText("");
                unitsText.setText("calories burned");
            }
        }
        else if (parent.getId() == R.id.exerciseSelect)
        {
            selectedExercise = parent.getSelectedItem().toString();
            Log.i("SELECTED EXERCISE!!!", selectedExercise);
            for (HashMap.Entry<String, Float> entry : exercisesWithAmountsNoUnits.entrySet())
            {
                String key = entry.getKey();

                if (key.equals(selectedExercise))
                {
                    unitsText.setText(exerciseToUnits.get(key));
                    break;
                }
            }
        }
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
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
