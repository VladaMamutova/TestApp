package com.vlada.testapp;

import android.graphics.Color;

public class MainCircle extends SimpleCircle {
    public static final int INIT_RADIUS = 60;
    public static final int MAIN_SPEED = 30;

    public MainCircle(int x, int y) {
        super(x, y, INIT_RADIUS);
        setColor(Color.BLUE);
    }

    public void moveMainCircleTouchAt(int x1, int y1) {
        //int dx = (x1 - x) * MAIN_SPEED / GameManager.getWidth();
        //int dy = (y1 - y) * MAIN_SPEED / GameManager.getHeight();
        //x += dx;
        //y += dy;
        x = x1;
        y = y1;
    }

    public SimpleCircle getCircleArea() {
        return new SimpleCircle(x, y, radius * 3);
    }

    public void initRadius() {
        radius = INIT_RADIUS;
    }

    public void growRadius(SimpleCircle circle) {
        // pi * newRadius ^ 2 = pi * radius ^ 2 + pi * radius2 ^ 2;
        // newRadius = sqrt(radius ^ 2 + radius2 ^ 2);
        radius = (int)Math.sqrt(Math.pow(radius, 2) + Math.pow(circle.radius, 2));
    }
}
