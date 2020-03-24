package com.blubber.snek.entity;

public enum EntityDirection {
    LEFT("RIGHT"), RIGHT("LEFT"), UP("DOWN"), DOWN("UP"), NONE;

    private String opposite;

    EntityDirection(){}

    EntityDirection(String opposite){ this.setOpposite(opposite); }

    public String opposite() { return opposite; }
    public void setOpposite(String opposite) { this.opposite = opposite; }
}
