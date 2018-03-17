package edu.auburn.eng.csse.comp3710.two0005.midterm18;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tanneroakes6 on 3/16/18.
 */

public class DisplayFragment extends Fragment {

    private TextView haiku;

    private static final String VIEW1 = "view1";
    private static final String VIEW2 = "view2";
    private static final String VIEW3 = "view3";

    private String fullHaiku;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String line1 = (String) getArguments().get(VIEW1);
        String line2 = (String) getArguments().get(VIEW2);
        String line3 = (String) getArguments().get(VIEW3);

        fullHaiku = line1 + "\n" + line2 + "\n" + line3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display, container, false);

        haiku = (TextView) v.findViewById(R.id.haikuView);
        haiku.setText(fullHaiku);

        return v;
    }

    public static DisplayFragment newInstance(String line1, String line2, String line3) {
        Bundle args = new Bundle();
        args.putString(VIEW1, line1);
        args.putString(VIEW2, line2);
        args.putString(VIEW3, line3);

        DisplayFragment fragment = new DisplayFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
