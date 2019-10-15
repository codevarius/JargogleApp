package com.kshv.example.jargogle_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kshv.example.jargogle_app.ui.main.MainFragment;

public class MainJargogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ()
                    .replace (R.id.container, MainFragment.newInstance())
                    .commitNow ();
        }
    }
}
