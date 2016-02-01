package crunchtime.cs160.com.crunchtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Main extends AppCompatActivity
{
    protected float currInputtedNumber;

    protected EditText numberInputField;
    protected TextWatcher numberInputFieldWatcher;

    protected Spinner modeSelector;
    protected ArrayAdapter<CharSequence> modeSelectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        modeSelector = (Spinner) findViewById(R.id.modeSelector);
        modeSelectorAdapter = ArrayAdapter.createFromResource(this, R.array.modes, R.layout.support_simple_spinner_dropdown_item);
        modeSelector.setAdapter(modeSelectorAdapter);
    }
}
