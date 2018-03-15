package edu.auburn.eng.csse.comp3710.two0005.midterm18;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by tanneroakes6 on 3/14/18.
 */

public class MainInteractionFragment extends Fragment {

    private RadioGroup mRadioGroup;
    private RadioButton mAdjectivesRadioButton;
    private RadioButton mNounsRadioButton;
    private RadioButton mVerbsRadioButton;
    private RadioButton mOtherRadioButton;
    private Button mAddWordButton;
    private Button mDisplayHaikuButton;
    private Button mStartOverButton;
    private Button mDeleteWordButton;
    private Spinner mWordSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_interaction, container, false);

        // hides buttons before user action
        mAddWordButton = (Button) v.findViewById(R.id.addButton);
        mAddWordButton.setVisibility(View.INVISIBLE);
        mWordSpinner = (Spinner) v.findViewById(R.id.spinner);
        mWordSpinner.setVisibility(View.INVISIBLE);
        mDeleteWordButton = (Button) v.findViewById(R.id.deleteButton);
        mDeleteWordButton.setVisibility(View.INVISIBLE);

        mRadioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //depending on which was clicked... populate the spinner accordingly
                RadioButton clickedButton = (RadioButton) radioGroup.findViewById(i);
                boolean isChecked = clickedButton.isChecked();
                if (isChecked) {
                    mAddWordButton.setVisibility(View.VISIBLE);
                    mWordSpinner.setVisibility(View.VISIBLE);
                    //populate spinner w/ appropriate words and weights
                    //populate add button with appropriate text
                }
            }
        });

        //mAdjectivesRadioButton = (RadioButton) v.findViewById(R.id.adjectivesRadioButton);


        //mWordSpinner = (Spinner) v.findViewById(R.id.spinner);


        return v;
    }
}
