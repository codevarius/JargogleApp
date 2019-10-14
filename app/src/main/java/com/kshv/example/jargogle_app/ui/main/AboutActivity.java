package com.kshv.example.jargogle_app.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kshv.example.jargogle_app.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
    }
}
