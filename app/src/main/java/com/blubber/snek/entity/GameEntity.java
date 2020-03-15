package com.blubber.snek.entity;

public abstract class GameEntity {

    private int xPos, yPos;
    private int color;


    public GameEntity(int xPos, int yPos){
        setXPos(xPos);
        setYPos(yPos);
    }

    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    public int getColor() { return color; }

    public void setXPos(int xPos) { this.xPos = xPos; }
    public void setYPos(int yPos) { this.yPos = yPos;}
    public void setColor(int color) { this.color = color; }
}
