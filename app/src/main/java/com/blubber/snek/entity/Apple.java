package com.blubber.snek.entity;

import com.blubber.snek.enums.ColorPalette;

public class Apple extends GameEntity{

    public Apple(int xPos, int yPos) {
        super(xPos, yPos);
        setColor(ColorPalette.PINK.getRgb());
    }
}
