package com.blubber.snek;

import androidx.annotation.NonNull;

import com.blubber.snek.entity.Apple;
import com.blubber.snek.entity.GameEntity;
import com.blubber.snek.entity.Snake;

class Board {
    private int CELL_COUNT_PER_ROW, CELL_COUNT_PER_COLUMN, CELL_SIZE, BOARD_WIDTH, BOARD_HEIGHT;
    private int BOARD_START_X, BOARD_START_Y = 0;
    private GameEntity[][] board;

    Board(int rowCount, int colCount){
        this.CELL_COUNT_PER_ROW = rowCount;
        this.CELL_COUNT_PER_COLUMN = colCount;
        board = new GameEntity[CELL_COUNT_PER_ROW][CELL_COUNT_PER_COLUMN];
    }

    GameEntity getCell(int xPos, int yPos){
        return this.board[yPos][xPos];
    }

    void setCell(int xPos, int yPos, GameEntity gameEntity){
        this.board[yPos][xPos] = gameEntity;
    }

    boolean allCellsFilled(){
        for (GameEntity[] gameEntities : board) {
            for (GameEntity gameEntity : gameEntities) {
                if (gameEntity == null) return false;
//                else if (gameEntity.getClass() != Snake.class) return false;
            }
        }
        return true;
    }

    void setCellSize(int width, int height){
        if (CELL_COUNT_PER_COLUMN >= CELL_COUNT_PER_ROW){
            this.CELL_SIZE = width/ CELL_COUNT_PER_COLUMN;
        } else {
            this.CELL_SIZE = height/ CELL_COUNT_PER_ROW;
        }
        this.BOARD_WIDTH = CELL_SIZE * CELL_COUNT_PER_COLUMN;
        this.BOARD_HEIGHT = CELL_SIZE * CELL_COUNT_PER_ROW;
    }

    void alignToCenter(int width, int height){
        if (CELL_COUNT_PER_COLUMN >= CELL_COUNT_PER_ROW){
            this.BOARD_START_Y = getCenter(height, BOARD_HEIGHT);
        } else {
            this.BOARD_START_X = getCenter(width, BOARD_WIDTH);
        }
    }

    private int getCenter(int lengthOfBackground, int lengthOfObject){
        return (lengthOfBackground - lengthOfObject) / 2;
    }

    int getWidth(){ return BOARD_WIDTH; }
    int getHeight(){ return BOARD_HEIGHT; }
    int getStartX(){ return BOARD_START_X; }
    int getStartY(){ return BOARD_START_Y; }
    int getCellSize() { return CELL_SIZE; }
    int getColCount() { return CELL_COUNT_PER_COLUMN; }
    int getRowCount() { return CELL_COUNT_PER_ROW; }

    /* For Debugging */

    @Override
    @NonNull
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (GameEntity[] gameEntities : board){
            for (GameEntity gameEntity : gameEntities){
                if (gameEntity == null) sb.append("0");
                else if(gameEntity.getClass() == Snake.class) sb.append("1");
                else if(gameEntity.getClass() == Apple.class) sb.append("2");
            } sb.append("\n");
        }
        return sb.toString();
    }
}
