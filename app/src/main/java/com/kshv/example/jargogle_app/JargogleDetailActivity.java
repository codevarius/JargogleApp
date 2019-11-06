package com.kshv.example.jargogle_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.kshv.example.jargogle_app.model.Jargogle;
import com.kshv.example.jargogle_app.model.JargogleDataProvider;
import com.kshv.example.jargogle_app.ui.main.JargogleDetailFragment;
import java.util.List;
import java.util.Objects;

public class JargogleDetailActivity extends AppCompatActivity {

    private Jargogle jargogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        SpannableString s = new SpannableString(getResources().getString(R.string.details));
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(s);

        setContentView (R.layout.jargogle_detail_activity);
        boolean incoming_jargogle = getIntent ().getType() != null &&
                getIntent ().getType().equals("text/plain");

        if (incoming_jargogle){
            jargogle = new Jargogle ();
            String message = getIntent ().getStringExtra (Intent.EXTRA_TEXT);
            jargogle.setData (getIntent ().getStringExtra (Intent.EXTRA_TEXT));

            System.out.println("MESSAGE "+message);
            if (message.contains ("\0")){
                jargogle.setEncoded (Jargogle.ENCODED);
            }

            JargogleDataProvider.getInstance (JargogleDetailActivity.this).addJargogle (jargogle);
        }

        List<Jargogle> jargogleList = JargogleDataProvider.getInstance (this).getJargogleList ();
        ViewPager viewPager = findViewById (R.id.detail_container);
        viewPager.setAdapter (new FragmentStatePagerAdapter (getSupportFragmentManager (),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (!incoming_jargogle) {
                    jargogle = jargogleList.get (position);
                }
                return JargogleDetailFragment.newInstance (jargogle.getUUID ());
            }

            @Override
            public int getCount() {
                return jargogleList.size ();
            }
        });

        viewPager.setCurrentItem (getIntent ()
                .getIntExtra (Jargogle.JARGOGLE_POSITION, 0));
    }
}
