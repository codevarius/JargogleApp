package com.kshv.example.jargogle_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.kshv.example.jargogle_app.ui.main.MainFragment;

import java.util.Objects;

public class MainJargogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main_activity);


        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ()
                    .replace (R.id.container, MainFragment.newInstance())
                    .commitNow ();
        }
    }
}
