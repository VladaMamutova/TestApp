package com.vlada.testapp;

import android.graphics.Color;

import java.util.Random;

public class EnemyCircle extends SimpleCircle {
    protected static final int MAX_X;
    protected static final int MAX_Y;
    protected static final int MAX_RADIUS;
    protected static final int MIN_RADIUS;
    protected static final int MAX_CIRCLES_IN_WIDTH = 25;
    protected static final int MIN_CIRCLES_IN_WIDTH = 10;
    private static final int RANDOM_SPEED = 10;
    private static final int FOOD_COLOR = Color.rgb(0, 200, 0);
    private static final int ENEMY_COLOR = Color.RED;
    private int dx;
    private int dy;

    static {
        MAX_X = GameManager.getWidth();
        MAX_Y = GameManager.getHeight();
        MAX_RADIUS = MAX_X / MIN_CIRCLES_IN_WIDTH;
        MIN_RADIUS = MAX_X / MAX_CIRCLES_IN_WIDTH;
    }

    public EnemyCircle(int x, int y, int radius, int dx, int dy) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public static EnemyCircle getRandomCircle(boolean isFood) {
        Random random = new Random();
        int x = random.nextInt(MAX_X);
        int y = random.nextInt(MAX_Y);
        int dx = 1 + random.nextInt(RANDOM_SPEED);
        int dy = 1 + random.nextInt(RANDOM_SPEED);
        int radius = MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS);
        EnemyCircle circle = new EnemyCircle(x, y, radius, dx, dy);
        circle.setColor(isFood ? FOOD_COLOR : ENEMY_COLOR);
        return circle;
    }

    public void setColorDependsOnItType(MainCircle mainCircle) {
        if(isSmallerThan(mainCircle)){
            setColor(FOOD_COLOR);
        }
        else setColor(ENEMY_COLOR);
        
    }

    boolean isSmallerThan(SimpleCircle circle) {
        return radius < circle.radius;
    }

    public void moveOneStep() {
        //x += dx;
        y += dy;
        checkBounds();
    }

    private void checkBounds() {
        //if(x > GameManager.getWidth() || x < 0){
          //  dx = -dx;
        //}
        if(y > GameManager.getHeight() || y < 0){
            //dy = -dy;
            //dy = 0;
            Random random = new Random();
            x = random.nextInt(MAX_X);
            y = 0;
        }
    }
}
