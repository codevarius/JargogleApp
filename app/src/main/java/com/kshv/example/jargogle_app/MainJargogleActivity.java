package com.kshv.example.jargogle_app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import androidx.appcompat.app.AppCompatActivity;
import com.kshv.example.jargogle_app.ui.main.MainFragment;
import java.util.Objects;

public class MainJargogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main_activity);

        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        Objects.requireNonNull(getSupportActionBar())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(s);

        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ()
                    .replace (R.id.container, MainFragment.newInstance())
                    .commitNow ();
        }
    }
}
