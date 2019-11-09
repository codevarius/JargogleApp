package com.kshv.example.jargogle_app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TypefaceSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kshv.example.jargogle_app.model.JargogleDataProvider;

import java.util.Objects;

import static android.text.Html.fromHtml;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String hexTop = JargogleDataProvider.getInstance(getApplicationContext()).getSavedJargogleGradient()[0];
        Objects.requireNonNull(getSupportActionBar())
                .setBackgroundDrawable(new ColorDrawable(Color.parseColor(hexTop)));
        getWindow().setStatusBarColor(Color.parseColor(hexTop));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SpannableString s = new SpannableString(getResources().getString(R.string.about));
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(s);

        TextView playStoreTextView = findViewById(R.id.play_store_link);
        Spanned link = fromHtml("<a href=\"https://play.google.com" +
                "/store/apps/developer?id=LoFiBit\">LoFiBit Google Play</a>");
        playStoreTextView.setText(link);
        playStoreTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView githubLink = findViewById(R.id.github_link);
        link = fromHtml("<a href" +
                "=\"https://github.com/codevarius/JargogleApp\">Jargogle on GitHub</a>");
        githubLink.setText(link);
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
