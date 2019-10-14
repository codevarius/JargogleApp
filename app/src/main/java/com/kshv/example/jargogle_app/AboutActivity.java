package com.kshv.example.jargogle_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kshv.example.jargogle_app.R;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled (true);
    }
}
