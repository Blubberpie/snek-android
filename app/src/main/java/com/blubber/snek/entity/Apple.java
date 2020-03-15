package com.blubber.snek.entity;

import com.blubber.snek.enums.GameColor;

public class Apple extends GameEntity{

    public Apple(int xPos, int yPos) {
        super(xPos, yPos);
        setColor(GameColor.PINK.getRgb());
    }
}
