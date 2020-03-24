package com.blubber.snek;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.blubber.snek.entity.Apple;
import com.blubber.snek.entity.EntityDirection;
import com.blubber.snek.entity.GameEntity;
import com.blubber.snek.entity.Snake;
import com.blubber.snek.enums.GameColor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A View class that handles the Snake game
 * @author Zwel Pai
 * @author https://github.com/Blubberpie/
 * @version 1.0
 */
public class GameBoardView extends AppCompatImageView {

    private Paint paint = new Paint();
    private Handler handler = new Handler();
    private Random random = new Random();

    /* Constants */
    private int MAX_APPLE_COUNT, INITIAL_SNAKE_LENGTH;

    /* Variables */
    private Snake snakeHead, snakeEnd;
    private Set<Apple> apples;
    private int moves;
    private boolean gameOver = false;
    private Board board;
    private int delay = 500;

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.MAX_APPLE_COUNT = 1;
        this.INITIAL_SNAKE_LENGTH = 1;
        this.apples = new HashSet<>();
        this.board = new Board(10, 9);
        initBoard();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            move();
            invalidate();
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        drawAll(canvas);
        handler.postDelayed(runnable, delay);
    }

    /* ==== INITIAL METHODS ==== */

    private void initBoard(){
        initSnake();
        spawnAllApples();
    }

    private void initSnake(){
        this.snakeHead = new Snake(0, 0, INITIAL_SNAKE_LENGTH, GameColor.DARK_YELLOW.getRgb());
        this.snakeHead.setDirection(EntityDirection.RIGHT);
        board.setCell(0, 0, snakeHead);
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

    private void spawnAllApples(){
        for (int i=0; i<MAX_APPLE_COUNT; i++) spawnApple();
    }

    /* ====================== */
    /* ==== GAME METHODS ==== */

    public void move(){
        if(!gameOver){
            this.moves++;
            int newX = this.snakeHead.getXPos();
            int newY = this.snakeHead.getYPos();
            EntityDirection snakeDir = this.snakeHead.getDirection();
            switch(snakeDir){
                case UP:
                    if (newY - 1 < 0){ newY = board.getRowCount() - 1; }
                    else newY--;
                    break;
                case DOWN:
                    if (newY + 1 >= board.getRowCount()){ newY = 0; }
                    else newY++;
                    break;
                case LEFT:
                    if (newX - 1 < 0){ newX = board.getColCount() - 1; }
                    else newX--;
                    break;
                case RIGHT:
                    if(newX + 1 >= board.getColCount()){ newX = 0; }
                    else newX++;
                    break;
                default:
                    break;
            }
            handleCollision(newX, newY);
        }
    }

    public void changeSnakeDirection(EntityDirection entityDirection){
        if(this.snakeHead.getDirection().opposite().compareTo(entityDirection.toString()) != 0 || this.snakeHead.getDirection().compareTo(entityDirection) == 0){
            this.snakeHead.setDirection(entityDirection);
        }
    }

    private void incrementSnake(){
        Snake newPart = new Snake(this.snakeEnd.getXPos(), this.snakeEnd.getYPos());
        this.snakeEnd.setTail(newPart);
        newPart.setHead(this.snakeEnd);
        this.snakeEnd = newPart;
        this.snakeHead.setLength(this.snakeHead.getLength() + 1);
    }

    private void followHead(int newX, int newY){
        Snake currentPart = snakeHead;
        int nextX = newX;
        int nextY = newY;
        board.setCell(snakeEnd.getXPos(), snakeEnd.getYPos(), null); // Clear tail
        do{
            int prevX = currentPart.getXPos();
            int prevY = currentPart.getYPos();
            currentPart.setXPos(nextX);
            currentPart.setYPos(nextY);
            this.board.setCell(nextX, nextY, currentPart);
            nextX = prevX;
            nextY = prevY;
            currentPart = currentPart.getTail();
        }
        while(currentPart != null);
    }

    private void handleCollision(int newX, int newY){
        GameEntity collidedEntity = board.getCell(newX, newY);
        if (collidedEntity != null) {
            if (collidedEntity.getClass() == Snake.class) gameOver = true;
            else if (collidedEntity.getClass() == Apple.class){
                followHead(newX, newY);
                spawnApple();
                incrementSnake();
                this.apples.remove((Apple) collidedEntity);
            }
        } else followHead(newX, newY);
    }

    private void spawnApple(){
        int spawnX;
        int spawnY;
        do{
            spawnX = random.nextInt(board.getColCount());
            spawnY = random.nextInt(board.getRowCount());
        }
        while(board.getCell(spawnX, spawnY) != null);

        Apple newApple = new Apple(spawnX, spawnY);
        this.apples.add(newApple);
        board.setCell(spawnX, spawnY, newApple);
    }

    private boolean checkAllCellsFilled(){
        if(snakeHead.getLength() == board.getRowCount() * board.getColCount()){
            return board.allCellsFilled();
        } return false;
    }

    /* ========================= */
    /* ==== DRAWING METHODS ==== */

    public void drawAll(Canvas canvas){
        // Fill Background
        this.paint.setColor(GameColor.PURPLE.getRgb());
        canvas.drawPaint(this.paint);

        board.setCellSize(getWidth(), getHeight());
        board.alignToCenter(getWidth(), getHeight());

        this.paint.setColor(GameColor.TEAL.getRgb());
        drawBoard(canvas);

        if(gameOver) {
            this.paint.setColor(Color.RED);
            drawBoard(canvas);
        } else if(checkAllCellsFilled()){
            this.paint.setColor(GameColor.PEACH.getRgb());
            drawBoard(canvas);
        } else {
            drawApples(canvas);
            drawSnake(canvas);
        }
    }

    private void drawApples(Canvas canvas){
        for (Apple a : this.apples){ drawEntity(canvas, a); }
    }

    private void drawSnake(Canvas canvas){
        Snake nextPart = snakeEnd;
        do{
            drawEntity(canvas, nextPart);
            nextPart = nextPart.getHead();
        }
        while(nextPart != null);
    }

    private void drawBoard(Canvas canvas){
        canvas.drawRect(board.getStartX(), board.getStartY(), board.getStartX() + board.getWidth(), board.getStartY() + board.getHeight(), this.paint);
    }

    private void drawEntity(Canvas canvas, GameEntity entity){
        this.paint.setColor(entity.getColor());
        int actualXPos = board.getStartX() + ((entity.getXPos()) * board.getCellSize());
        int actualYPos = board.getStartY() + ((entity.getYPos()) * board.getCellSize());
        canvas.drawRect(actualXPos, actualYPos, actualXPos + board.getCellSize(), actualYPos + board.getCellSize(), this.paint);
    }
    /* =============== */
}
