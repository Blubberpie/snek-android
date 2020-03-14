package com.blubber.snek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.game_board).getRootView().setBackgroundColor(ColorPalette.PURPLE.getRgb());
        findViewById(R.id.up_button).setBackgroundColor(ColorPalette.PEACH.getRgb());
        findViewById(R.id.right_button).setBackgroundColor(ColorPalette.PEACH.getRgb());
        findViewById(R.id.down_button).setBackgroundColor(ColorPalette.PEACH.getRgb());
        findViewById(R.id.left_button).setBackgroundColor(ColorPalette.PEACH.getRgb());
    }
}
