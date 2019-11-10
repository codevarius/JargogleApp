package com.kshv.example.jargogle_app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;

import androidx.appcompat.app.AppCompatActivity;

import com.kshv.example.jargogle_app.model.JargogleDataProvider;
import com.kshv.example.jargogle_app.ui.main.LoginFragment;
import com.kshv.example.jargogle_app.ui.main.MainFragment;

import java.util.Objects;

public class MainJargogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //create a new gradient color
        String hexTop = JargogleDataProvider.getInstance(getApplicationContext()).getSavedJargogleGradient(getApplicationContext())[0];
        String hexBottom = JargogleDataProvider.getInstance(getApplicationContext()).getSavedJargogleGradient(getApplicationContext())[1];

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, new int[]{
                Color.parseColor(hexBottom),
                Color.parseColor(hexTop)});
        getWindow().setBackgroundDrawable(gd);

        Objects.requireNonNull(getSupportActionBar())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(s);

        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (LoginFragment.currentUsr == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
