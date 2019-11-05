package com.kshv.example.jargogle_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.TypefaceSpan;
import android.widget.TextView;

import com.kshv.example.jargogle_app.R;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled (true);

        SpannableString s = new SpannableString(getResources().getString(R.string.about));
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(s);

        TextView playStoreTextView = findViewById (R.id.play_store_link);
        Spanned link = Html.fromHtml ("<a href=\"https://play.google.com" +
                "/store/apps/developer?id=LoFiBit\">LoFiBit Google Play</a>");
        playStoreTextView.setText (link);
        playStoreTextView.setMovementMethod (LinkMovementMethod.getInstance ());

        TextView githubLink = findViewById (R.id.github_link);
        link = Html.fromHtml ("<a href" +
                "=\"https://github.com/codevarius/JargogleApp\">Jargogle on GitHub</a>");
        githubLink.setText (link);
        githubLink.setMovementMethod (LinkMovementMethod.getInstance ());
    }
}
