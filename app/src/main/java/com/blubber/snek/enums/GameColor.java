package com.blubber.snek.enums;

import android.graphics.Color;

public enum GameColor {
    PURPLE(Color.rgb(84,70,103)),
    TEAL(Color.rgb(19,128,133)),
    PEACH(Color.rgb(220,134,101)),
    PINK(Color.rgb(206,118,114)),
    YELLOW(Color.rgb(238,180,98)),
    DARK_YELLOW(Color.rgb(176, 135, 69));

    private int rgb;

    GameColor(int rgb){
        this.rgb = rgb;
    }

    public int getRgb() {
        return this.rgb;
    }
}
