package com.blubber.snek;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import com.blubber.snek.entity.Apple;
import com.blubber.snek.entity.EntityDirection;
import com.blubber.snek.entity.GameEntity;
import com.blubber.snek.entity.Snake;
import com.blubber.snek.enums.ColorPalette;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameBoardView extends AppCompatImageView {

    private Paint paint = new Paint();
    private Handler handler = new Handler();
    private Random random = new Random();

    /* Constants */
    private int CELL_SIZE, CELL_COUNT_X, CELL_COUNT_Y, BOARD_WIDTH, BOARD_HEIGHT, MAX_APPLE_COUNT;
    private int BOARD_START_X, BOARD_START_Y = 0;

    /* Variables */
    private int snakeLen, appleCount;
    private Snake snakeHead;
    private Set<Apple> apples;
    private boolean alive = true;
    private GameEntity[][] board;

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.CELL_COUNT_X = 10;
        this.CELL_COUNT_Y = 11;
        this.snakeLen = 3;
        this.MAX_APPLE_COUNT = 1;
        this.apples = new HashSet<>();
        this.board = new GameEntity[CELL_COUNT_X][CELL_COUNT_Y];
        initBoard();
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

            for (Apple a : this.apples){
                drawEntity(canvas, a);
            }

            drawEntity(canvas, snakeHead);
        }
    }

    private void drawEntity(Canvas canvas, GameEntity entity){
        this.paint.setColor(entity.getColor());
        int actualXPos = BOARD_START_X + ((entity.getXPos()) * CELL_SIZE);
        int actualYPos = BOARD_START_Y + ((entity.getYPos()) * CELL_SIZE);
        canvas.drawRect(actualXPos, actualYPos, actualXPos + CELL_SIZE, actualYPos + CELL_SIZE, this.paint);
    }

    public void changeSnakeDirection(EntityDirection entityDirection){
        this.snakeHead.setDirection(entityDirection);
    }

    public void move(){
        int initialX = this.snakeHead.getXPos();
        int initialY = this.snakeHead.getYPos();
        EntityDirection snakeDir = this.snakeHead.getDirection();
        switch(snakeDir){
            case UP:
                if (initialY - 1 < 0){ this.snakeHead.setYPos(board[0].length - 1); }
                else this.snakeHead.setYPos(initialY - 1);
                break;
            case DOWN:
                if (initialY + 1 >= board[0].length){ snakeHead.setYPos(0); }
                else this.snakeHead.setYPos(initialY + 1);
                break;
            case LEFT:
                if (initialX - 1 < 0){ snakeHead.setXPos(board.length - 1); }
                else snakeHead.setXPos(initialX - 1);
                break;
            case RIGHT:
                if(initialX + 1 >= board.length){ snakeHead.setXPos(0); }
                else snakeHead.setXPos(initialX + 1);
                break;
            default:
                break;
        }
        this.board[this.snakeHead.getXPos()][this.snakeHead.getYPos()] = snakeHead;
        followHead(this.snakeHead);
    }

    private void followHead(Snake snakeHead){

    }

    private void spawnApple(){
        int spawnX = random.nextInt(CELL_COUNT_X);
        int spawnY = random.nextInt(CELL_COUNT_Y);

        Apple newApple = new Apple(spawnX, spawnY);
        this.apples.add(newApple);
        board[spawnX][spawnY] = newApple;
    }

    private void initBoard(){
        initSnake();
        for (int i=0; i<MAX_APPLE_COUNT; i++) spawnApple();
        this.appleCount = MAX_APPLE_COUNT;
    }

    private void initSnake(){
        this.snakeHead = new Snake(0, 0);
        changeSnakeDirection(EntityDirection.RIGHT);
        this.board[0][0] = this.snakeHead;
        Snake nextHead = this.snakeHead;
        for (int i=0; i<snakeLen -1; i++){
            nextHead.setTail(new Snake(0,0));
            nextHead = nextHead.getTail();
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
