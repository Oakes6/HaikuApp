package edu.auburn.eng.csse.comp3710.two0005.midterm18;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Dictionary;
import java.util.HashMap;

/**
 * Created by tanneroakes6 on 3/14/18.
 */

public class MainInteractionFragment extends Fragment {

    private RadioGroup mRadioGroup;

    private Button mAddWordButton;
    private Button mDisplayHaikuButton;
    private Button mStartOverButton;
    private Button mDeleteWordButton;
    private Spinner mWordSpinner;

    private String[] adjectivesArr;
    private String[] nounsArr;
    private String[] verbsArr;
    private String[] otherArr;

    private HashMap<String, Integer> syllableDict;

    private EditText mHaikuLine1;
    private EditText mHaikuLine2;
    private EditText mHaikuLine3;

    private int currentHaikuLineSyllables;
    private int currentHaikuLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syllableDict = new HashMap<>();

        adjectivesArr = getResources().getStringArray(R.array.adjectives);
        String temp;
        for (int i = 0; i < adjectivesArr.length; i++) {
            int num = Character.getNumericValue(adjectivesArr[i].charAt(0));
            temp = adjectivesArr[i].substring(1, adjectivesArr[i].length());
            syllableDict.put(temp, num);
            adjectivesArr[i] = temp;
        }

        nounsArr = getResources().getStringArray(R.array.nouns);
        for (int i = 0; i < nounsArr.length; i++) {
            int num = Character.getNumericValue(nounsArr[i].charAt(0));
            temp = nounsArr[i].substring(1, nounsArr[i].length());
            syllableDict.put(temp, num);
            nounsArr[i] = temp;
        }

        verbsArr = getResources().getStringArray(R.array.verbs);
        for (int i = 0; i < verbsArr.length; i++) {
            int num = Character.getNumericValue(verbsArr[i].charAt(0));
            temp = verbsArr[i].substring(1,verbsArr[i].length());
            syllableDict.put(temp, num);
            verbsArr[i] = temp;
        }

        otherArr = getResources().getStringArray(R.array.other);
        for (int i = 0; i < otherArr.length; i++) {
            int num = Character.getNumericValue(otherArr[i].charAt(0));
            temp = otherArr[i].substring(1, otherArr[i].length());
            syllableDict.put(temp, num);
            otherArr[i] = temp;
        }

        currentHaikuLine = 1;
        currentHaikuLineSyllables = 0;
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
                mAddWordButton.setVisibility(View.VISIBLE);
                mWordSpinner.setVisibility(View.VISIBLE);

                //populate spinner w/ appropriate words and weights
                //populate add button with appropriate text
                ArrayAdapter<CharSequence> adapter = populateArrayAdapter(i);
                mWordSpinner.setAdapter(adapter);
            }
        });


        return v;
    }

    private ArrayAdapter<CharSequence> populateArrayAdapter(int id) {

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item);
        //populate spinner w/ appropriate words and weights
        //populate add button with appropriate text
        if (id == R.id.adjectivesRadioButton) {

            if (currentHaikuLine == 2) {
                if (currentHaikuLineSyllables <= 3 ) {
                    adapter.addAll(adjectivesArr);
                }
                else if (currentHaikuLineSyllables == 4) {
                    // add all 3, 2, and 1
                    for (int i = 0; i < adjectivesArr.length; i++) {
                        if (syllableDict.get(adjectivesArr[i]) < 4) {
                            adapter.add(adjectivesArr[i]);
                        }
                    }
                }
                else if (currentHaikuLineSyllables == 5) {
                    // load all 2, and 1
                    for (int i = 0; i < adjectivesArr.length; i++) {
                        if (syllableDict.get(adjectivesArr[i]) < 3) {
                            adapter.add(adjectivesArr[i]);
                        }
                    }
                }
                else {
                    // load all 1
                    for (int i = 0; i < adjectivesArr.length; i++) {
                        if (syllableDict.get(adjectivesArr[i]) < 2) {
                            adapter.add(adjectivesArr[i]);
                        }
                    }
                }
            }
            else {
                if (currentHaikuLineSyllables <= 1) {
                    // load all
                    adapter.addAll(adjectivesArr);
                }
                else if (currentHaikuLineSyllables == 2) {
                    // load all 3, 2, and 1
                    for (int i = 0; i < adjectivesArr.length; i++) {
                        if (syllableDict.get(adjectivesArr[i]) < 4) {
                            adapter.add(adjectivesArr[i]);
                        }
                    }
                }
                else if (currentHaikuLineSyllables == 3) {
                    // load all 2, and 1
                    for (int i = 0; i < adjectivesArr.length; i++) {
                        if (syllableDict.get(adjectivesArr[i]) < 3) {
                            adapter.add(adjectivesArr[i]);
                        }
                    }
                }
                else {
                    // load all 1
                    for (int i = 0; i < adjectivesArr.length; i++) {
                        if (syllableDict.get(adjectivesArr[i]) < 2) {
                            adapter.add(adjectivesArr[i]);
                        }
                    }
                }
            }
        }
        else if (id == R.id.nounsRadioButton) {
            adapter.addAll(nounsArr);
        }
        else if (id == R.id.verbsRadioButton) {
            adapter.addAll(verbsArr);
        }
        else {
            adapter.addAll(otherArr);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
