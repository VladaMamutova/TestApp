package com.vlada.testapp;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class GameManager {
    private static final int ENEMY_CIRCLES_COUNT = 8;
    private static final int FOOD_CIRCLES_COUNT = 10;
    private MainCircle mainCircle;
    private ArrayList<EnemyCircle> circles;
    private ArrayList<EnemyCircle> food;
    private static int width;
    private static int height;
    private CanvasView canvasView;
    private int gameProgress;

    public GameManager(CanvasView view, int _width, int _height) {
        canvasView = view;
        width = _width;
        height = _height;
        initMainCircle();
        initEnemyCircles();
        initFoodCircles();
    }

    public static int getWidth() { return width; }
    public static int getHeight() { return height; }

    private void initMainCircle() {
        mainCircle = new MainCircle(width / 2, height / 2);
    }

    private void initEnemyCircles() {
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        circles = new ArrayList<>();
        for (int i = 0; i < ENEMY_CIRCLES_COUNT; i++) {
            EnemyCircle circle;
            do {
                circle = EnemyCircle.getRandomCircle(false);
            }
            while (circle.isIntersect(mainCircleArea));
            circles.add(circle);
        }
    }

    private void initFoodCircles() {
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        food = new ArrayList<>();
        for (int i = 0; i < FOOD_CIRCLES_COUNT; i++) {
            EnemyCircle circle;
            do {
                circle = EnemyCircle.getRandomCircle(true);
            }
            while (circle.isIntersect(mainCircleArea));
            food.add(circle);
        }
    }

    public void onDraw(){
        canvasView.drawCircle(mainCircle);
        for (EnemyCircle circle: circles
             ) {
            canvasView.drawCircle(circle);
        }
        for (EnemyCircle circle: food
        ) {
            canvasView.drawCircle(circle);
        }
        canvasView.UpdateProgress(gameProgress);
    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleTouchAt(x, y);
        checkCollision();
        moveCircles();
    }

    private void checkCollision() {
        SimpleCircle circleForDelete = null;
        for (EnemyCircle circle: circles
        ) {
            if (mainCircle.isIntersect(circle)) {
                gameOver("YOU LOSE");
                return;
            }

        }
        for (EnemyCircle circle : food) {
            if (mainCircle.isIntersect(circle)) {
                gameProgress+=10;
                //mainCircle.growRadius(circle);
                circleForDelete = circle;
                break;
            }
        }
        if(circleForDelete != null)
            food.remove(circleForDelete);
        if(gameProgress == 100){
            gameOver("YOU WIN");
        }
    }

    private void gameOver(String result) {
        canvasView.showMessage(result);
        mainCircle.initRadius();
        circles.removeAll(circles);
        initEnemyCircles();
        initFoodCircles();
        gameProgress = 0;
        canvasView.redraw();
    }

    private void moveCircles() {
        for (EnemyCircle circle: circles
             ) {
            circle.moveOneStep();
        }
        for (EnemyCircle circle: food
        ) {
            circle.moveOneStep();
        }
    }
}
