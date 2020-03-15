package com.blubber.snek.entity;

import com.blubber.snek.enums.ColorPalette;

public class Snake extends GameEntity {

    private Snake tail;

    public Snake(int xPos, int yPos) {
        super(xPos, yPos);
        tail = null;
        setColor(ColorPalette.YELLOW.getRgb());
    }

    public Snake getTail() { return tail; }
    public void setTail(Snake tail) { this.tail = tail; }
}
