package com.blubber.snek;

import android.graphics.Color;

public enum ColorPalette {
    PURPLE(Color.rgb(84,70,103)),
    TEAL(Color.rgb(19,128,133)),
    PEACH(Color.rgb(220,134,101)),
    PINK(Color.rgb(206,118,114)),
    YELLOW(Color.rgb(238,180,98));

    private int rgb;

    ColorPalette(int rgb){
        this.rgb = rgb;
    }

    public int getRgb() {
        return this.rgb;
    }
}
