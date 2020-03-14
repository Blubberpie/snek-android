package com.blubber.snek;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.Random;

public class GameBoardView extends AppCompatImageView {

    private Paint paint = new Paint();
    private Handler handler = new Handler();
    private Random random = new Random();

    private int CELL_SIZE,
            CELL_COUNT_X,
            CELL_COUNT_Y,
            BOARD_WIDTH,

            BOARD_HEIGHT,
            CURRENT_APPLE_COUNT,
            MAX_APPLE_COUNT;

    private int BOARD_START_X, BOARD_START_Y = 0;
    private boolean alive = true;
    private int[][] board;

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.CELL_COUNT_X = 20;
        this.CELL_COUNT_Y = 22;

        this.board = new int[CELL_COUNT_X][CELL_COUNT_Y];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        doDrawing(canvas);
    }

    public void doDrawing(Canvas canvas){
        if(alive){
            // Fill Background
            this.paint.setColor(ColorPalette.PURPLE.getRgb());
            canvas.drawPaint(this.paint);

            setCellSize();
            alignBoardToCenter();

            this.paint.setColor(ColorPalette.TEAL.getRgb());
            canvas.drawRect(BOARD_START_X, BOARD_START_Y, BOARD_START_X + BOARD_WIDTH, BOARD_START_Y + BOARD_HEIGHT, this.paint);
        }
    }

    private void spawnApple(){
        int spawnX = random.nextInt(CELL_COUNT_X);
        int spawnY = random.nextInt(CELL_COUNT_Y);
    }

    private void spawnAllApples(){
        for (int i=0; i<MAX_APPLE_COUNT; i++){

        }
    }

    private void setCellSize(){
        if (CELL_COUNT_X >= CELL_COUNT_Y){
            this.CELL_SIZE = getWidth()/CELL_COUNT_X;
        } else {
            this.CELL_SIZE = getHeight()/CELL_COUNT_Y;
        }
        this.BOARD_WIDTH = CELL_SIZE * CELL_COUNT_X;
        this.BOARD_HEIGHT = CELL_SIZE * CELL_COUNT_Y;
    }

    private void alignBoardToCenter(){
        if (CELL_COUNT_X >= CELL_COUNT_Y){
            this.BOARD_START_Y = getCenter(getHeight(), BOARD_HEIGHT);
        } else {
            this.BOARD_START_X = getCenter(getWidth(), BOARD_WIDTH);
        }
    }

    private int getCenter(int lengthOfBackground, int lengthOfObject){
        return (lengthOfBackground - lengthOfObject) / 2;
    }
}
