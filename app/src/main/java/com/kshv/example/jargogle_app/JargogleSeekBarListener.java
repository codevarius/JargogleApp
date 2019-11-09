package com.kshv.example.jargogle_app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.SeekBar;

import com.kshv.example.jargogle_app.model.JargogleDataProvider;

public class JargogleSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    private String hexTop,hexBottom;
    private SeekBar redBar,greenBar,blueBar;
    private Activity activity;

    public JargogleSeekBarListener(Activity activity,SeekBar ... bars){
        this.activity = activity;
        redBar = bars[0];
        greenBar = bars[1];
        blueBar = bars[2];
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //create a new gradient color
        hexTop = String.format("#%02x%02x%02x", redBar.getProgress(), greenBar.getProgress(), blueBar.getProgress());
        double color_k = 0.25;
        hexBottom = String.format("#%02x%02x%02x", Math.round(redBar.getProgress() * color_k),
                Math.round(greenBar.getProgress() * color_k), Math.round(blueBar.getProgress() * color_k));
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, new int[]{
                Color.parseColor(hexBottom),
                Color.parseColor(hexTop)});

        activity.getWindow().setBackgroundDrawable(gd); //getResources().getDrawable(R.drawable.gradient)
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        JargogleDataProvider.getInstance(activity.getApplicationContext()).updateJargogleGrdient(hexTop,hexBottom,redBar.getProgress(),
                greenBar.getProgress(),blueBar.getProgress());
    }
}
