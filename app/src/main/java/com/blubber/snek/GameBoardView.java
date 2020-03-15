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
import com.blubber.snek.enums.GameColor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameBoardView extends AppCompatImageView {

    private Paint paint = new Paint();
    private Handler handler = new Handler();
    private Random random = new Random();

    /* Constants */
    private int CELL_SIZE, CELL_COUNT_PER_COLUMN, CELL_COUNT_PER_ROW, BOARD_WIDTH, BOARD_HEIGHT, MAX_APPLE_COUNT, INITIAL_SNAKE_LENGTH;
    private int BOARD_START_X, BOARD_START_Y = 0;

    /* Variables */
    private Snake snakeHead, snakeEnd;
    private Set<Apple> apples;
    private int moves;
    private boolean alive = true;
    private GameEntity[][] board;

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.CELL_COUNT_PER_ROW = 11; // Y length
        this.CELL_COUNT_PER_COLUMN = 10; // X length
        this.MAX_APPLE_COUNT = 1;
        this.INITIAL_SNAKE_LENGTH = 5;
        this.apples = new HashSet<>();
        this.board = new GameEntity[CELL_COUNT_PER_ROW][CELL_COUNT_PER_COLUMN];
        initBoard();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        doDrawing(canvas);
    }

    public void doDrawing(Canvas canvas){
        if(alive){
            // Fill Background
            this.paint.setColor(GameColor.PURPLE.getRgb());
            canvas.drawPaint(this.paint);

            setCellSize();
            alignBoardToCenter();

            this.paint.setColor(GameColor.TEAL.getRgb());
            canvas.drawRect(BOARD_START_X, BOARD_START_Y, BOARD_START_X + BOARD_WIDTH, BOARD_START_Y + BOARD_HEIGHT, this.paint);

            for (Apple a : this.apples){
                drawEntity(canvas, a);
            }

            Snake nextPart = snakeEnd;
            do{
                Log.d("shit", nextPart.toString());
                drawEntity(canvas, nextPart);
                nextPart = nextPart.getHead();
            }
            while(nextPart != null);
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
        this.moves++;
        int newX = this.snakeHead.getXPos();
        int newY = this.snakeHead.getYPos();
        EntityDirection snakeDir = this.snakeHead.getDirection();
        switch(snakeDir){
            case UP:
                if (newY - 1 < 0){ newY = board.length - 1; }
                else newY--;
                break;
            case DOWN:
                if (newY + 1 >= board.length){ newY = 0; }
                else newY++;
                break;
            case LEFT:
                if (newX - 1 < 0){ newX = board[0].length - 1; }
                else newX--;
                break;
            case RIGHT:
                if(newX + 1 >= board[0].length){ newX = 0; }
                else newX++;
                break;
            default:
                break;
        }
        initiateTrail(newX, newY);
    }

    private void initiateTrail(int newX, int newY){
        Snake currentPart = snakeHead;
        int nextX = newX;
        int nextY = newY;
        do{
            int prevX = currentPart.getXPos();
            int prevY = currentPart.getYPos();
            currentPart.setXPos(nextX);
            currentPart.setYPos(nextY);
            this.board[nextY][nextX] = currentPart;
            nextX = prevX;
            nextY = prevY;
            currentPart = currentPart.getTail();
        }
        while(currentPart != null);
        // Clear initial cell only when the last snake tail leaves
        if (moves >= this.snakeHead.getLength()){ board[nextY][nextX] = null; }
        for (GameEntity[] g: board){
            Log.d("fuck", Arrays.toString(g));
        }
    }

    private void spawnApple(){
        int spawnX = random.nextInt(CELL_COUNT_PER_COLUMN);
        int spawnY = random.nextInt(CELL_COUNT_PER_ROW);

        Apple newApple = new Apple(spawnX, spawnY);
        this.apples.add(newApple);
        board[spawnY][spawnX] = newApple;
    }

    private void initBoard(){
        initSnake();
        for (int i=0; i<MAX_APPLE_COUNT; i++) spawnApple();
    }

    private void initSnake(){
        this.snakeHead = new Snake(0, 0, INITIAL_SNAKE_LENGTH, GameColor.DARK_YELLOW.getRgb());
        changeSnakeDirection(EntityDirection.RIGHT);
        this.board[0][0] = this.snakeHead;
        Snake prevHead = null;
        Snake nextHead = this.snakeHead;
        for (int i=0; i<INITIAL_SNAKE_LENGTH -1; i++){
            nextHead.setHead(prevHead);
            nextHead.setTail(new Snake(0,0));
            prevHead = nextHead;
            nextHead = nextHead.getTail();
        }
        nextHead.setHead(prevHead);
        this.snakeEnd = nextHead;
    }

    private void setCellSize(){
        if (CELL_COUNT_PER_COLUMN >= CELL_COUNT_PER_ROW){
            this.CELL_SIZE = getWidth()/ CELL_COUNT_PER_COLUMN;
        } else {
            this.CELL_SIZE = getHeight()/ CELL_COUNT_PER_ROW;
        }
        this.BOARD_WIDTH = CELL_SIZE * CELL_COUNT_PER_COLUMN;
        this.BOARD_HEIGHT = CELL_SIZE * CELL_COUNT_PER_ROW;
    }

    private void alignBoardToCenter(){
        if (CELL_COUNT_PER_COLUMN >= CELL_COUNT_PER_ROW){
            this.BOARD_START_Y = getCenter(getHeight(), BOARD_HEIGHT);
        } else {
            this.BOARD_START_X = getCenter(getWidth(), BOARD_WIDTH);
        }
    }

    private int getCenter(int lengthOfBackground, int lengthOfObject){
        return (lengthOfBackground - lengthOfObject) / 2;
    }
}
