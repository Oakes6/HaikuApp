package edu.auburn.eng.csse.comp3710.two0005.midterm18;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainInteractionActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainInteractionFragment();
    }
}
