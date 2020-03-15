package com.blubber.snek.entity;

import com.blubber.snek.enums.GameColor;

public class Snake extends GameEntity {

    private Snake head;
    private Snake tail;
    private int length;
    private EntityDirection direction;

    public Snake(int xPos, int yPos) {
        super(xPos, yPos);
        tail = null;
        setColor(GameColor.YELLOW.getRgb());
    }

    public Snake(int xPos, int yPos, int length, int color){
        this(xPos, yPos);
        this.length = length;
        setColor(color);
    }

    public Snake getHead() { return head; }
    public Snake getTail() { return tail; }
    public EntityDirection getDirection() { return direction; }
    public int getLength() { return length; }

    public void setHead(Snake head) { this.head = head; }
    public void setTail(Snake tail) { this.tail = tail; }
    public void setDirection(EntityDirection direction) { this.direction = direction; }
    public void setLength(int length) { this.length = length; }
}
