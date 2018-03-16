package edu.auburn.eng.csse.comp3710.two0005.midterm18;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    private TextView mHaikuLine1;
    private TextView mHaikuLine2;
    private TextView mHaikuLine3;

    private int currentHaikuLineSyllables;
    private int currentHaikuLine;
    private int currentRadioId;

    private boolean isHaikuFull;
    private boolean isCurrentLineFull;

    private String wordToAdd;
    private String line1;
    private String line2;
    private String line3;

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
        isHaikuFull = false;
        isCurrentLineFull = false;
        wordToAdd = "";
        currentRadioId = -1;
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

        mStartOverButton = (Button) v.findViewById(R.id.start_over_button);
        mStartOverButton.setVisibility(View.INVISIBLE);

        mDisplayHaikuButton = (Button) v.findViewById(R.id.display_haiku_button);
        mDisplayHaikuButton.setVisibility(View.INVISIBLE);

        mRadioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mAddWordButton.setVisibility(View.VISIBLE);
                mWordSpinner.setVisibility(View.VISIBLE);
                ArrayAdapter<CharSequence> adapter;
                //populate spinner w/ appropriate words and weights
                //populate add button with appropriate text
                if (i == R.id.adjectivesRadioButton) {
                    adapter = populateArrayAdapter(i, adjectivesArr);
                }
                else if (i == R.id.nounsRadioButton) {
                    adapter = populateArrayAdapter(i, nounsArr);
                }
                else if (i == R.id.verbsRadioButton) {
                    adapter = populateArrayAdapter(i, verbsArr);
                }
                else {
                    adapter = populateArrayAdapter(i, otherArr);
                }

                mWordSpinner.setAdapter(adapter);
            }
        });

        mWordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String change = mWordSpinner.getSelectedItem().toString().toUpperCase();
                String update = "ADD \"" + change + "\" TO THE HAIKU";
                wordToAdd = change.toLowerCase();
                mAddWordButton.setText(update);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        mHaikuLine1 = (TextView) v.findViewById(R.id.textView1);
        mHaikuLine2 = (TextView) v.findViewById(R.id.textView2);
        mHaikuLine3 = (TextView) v.findViewById(R.id.textView3);
        mAddWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteWordButton.setVisibility(View.VISIBLE);
                mDisplayHaikuButton.setVisibility(View.VISIBLE);
                mStartOverButton.setVisibility(View.VISIBLE);
                if (!isHaikuFull) {
                    ArrayAdapter<CharSequence> adapter;
                    //grab current line text and add word
                    if (currentHaikuLine == 2) {
                        int weight = syllableDict.get(wordToAdd);
                        currentHaikuLineSyllables += weight;
                        mHaikuLine2.append(" " + wordToAdd);
                        currentRadioId = mRadioGroup.getCheckedRadioButtonId();
                        if (currentHaikuLineSyllables == 7) {
                            currentHaikuLine++;
                            currentHaikuLineSyllables = 0;
                        }
                        if (currentRadioId == R.id.adjectivesRadioButton) {
                            adapter = populateArrayAdapter(currentRadioId, adjectivesArr);
                        }
                        else if (currentRadioId == R.id.nounsRadioButton) {
                            adapter = populateArrayAdapter(currentRadioId, nounsArr);
                        }
                        else if (currentRadioId == R.id.verbsRadioButton) {
                            adapter = populateArrayAdapter(currentRadioId, verbsArr);
                        }
                        else {
                            adapter = populateArrayAdapter(currentRadioId, otherArr);
                        }
                        mWordSpinner.setAdapter(adapter);
                    }
                    else if (currentHaikuLine == 1 || currentHaikuLine == 3) {
                        int weight = syllableDict.get(wordToAdd);
                        currentHaikuLineSyllables += weight;
                        currentRadioId = mRadioGroup.getCheckedRadioButtonId();
                        if (currentHaikuLine == 1) {
                            mHaikuLine1.append(" " + wordToAdd);
                        }
                        if (currentHaikuLine == 3) {
                            mHaikuLine3.append(" " + wordToAdd);
                        }
                        if (currentHaikuLineSyllables == 5) {
                            currentHaikuLine++;
                            currentHaikuLineSyllables = 0;
                        }
                        if (currentRadioId == R.id.adjectivesRadioButton) {
                            adapter = populateArrayAdapter(currentRadioId, adjectivesArr);
                        }
                        else if (currentRadioId == R.id.nounsRadioButton) {
                            adapter = populateArrayAdapter(currentRadioId, nounsArr);
                        }
                        else if (currentRadioId == R.id.verbsRadioButton) {
                            adapter = populateArrayAdapter(currentRadioId, verbsArr);
                        }
                        else {
                            adapter = populateArrayAdapter(currentRadioId, otherArr);
                        }
                        mWordSpinner.setAdapter(adapter);
                    }
                    else {
                        isHaikuFull = true;
                    }
                }
                else {
                    return;
                }
            }
        });


        return v;
    }

    private ArrayAdapter<CharSequence> populateArrayAdapter(int id, String[] array) {

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item);
        currentRadioId = id;
        //populate spinner w/ appropriate words and weights
        //populate add button with appropriate text
        if (currentHaikuLine == 2) {
            if (currentHaikuLineSyllables <= 3 ) {
                adapter.addAll(array);
            }
            else if (currentHaikuLineSyllables == 4) {
                // add all 3, 2, and 1
                for (int i = 0; i < array.length; i++) {
                    if (syllableDict.get(array[i]) < 4) {
                        adapter.add(array[i]);
                    }
                }
            }
            else if (currentHaikuLineSyllables == 5) {
                // load all 2, and 1
                for (int i = 0; i < array.length; i++) {
                    if (syllableDict.get(array[i]) < 3) {
                        adapter.add(array[i]);
                    }
                }
            }
            else {
                // load all 1
                for (int i = 0; i < array.length; i++) {
                    if (syllableDict.get(array[i]) < 2) {
                        adapter.add(array[i]);
                    }
                }
            }
        }
        else {
            if (currentHaikuLineSyllables <= 1) {
                // load all
                adapter.addAll(array);
            }
            else if (currentHaikuLineSyllables == 2) {
                // load all 3, 2, and 1
                for (int i = 0; i < array.length; i++) {
                    if (syllableDict.get(array[i]) < 4) {
                        adapter.add(array[i]);
                    }
                }
            }
            else if (currentHaikuLineSyllables == 3) {
                // load all 2, and 1
                for (int i = 0; i < array.length; i++) {
                    if (syllableDict.get(array[i]) < 3) {
                        adapter.add(array[i]);
                    }
                }
            }
            else {
                // load all 1
                for (int i = 0; i < array.length; i++) {
                    if (syllableDict.get(array[i]) < 2) {
                        adapter.add(array[i]);
                    }
                }
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
