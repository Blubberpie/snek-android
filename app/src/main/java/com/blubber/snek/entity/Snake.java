package com.blubber.snek.entity;

import com.blubber.snek.enums.ColorPalette;

public class Snake extends GameEntity {

    private Snake tail;
    private EntityDirection direction;

    public Snake(int xPos, int yPos) {
        super(xPos, yPos);
        tail = null;
        setColor(ColorPalette.YELLOW.getRgb());
    }

    public Snake getTail() { return tail; }
    public EntityDirection getDirection() { return direction; }
    public void setTail(Snake tail) { this.tail = tail; }
    public void setDirection(EntityDirection direction) { this.direction = direction; }

}
