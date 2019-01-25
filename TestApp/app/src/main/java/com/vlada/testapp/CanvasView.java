package com.vlada.testapp;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.*;
import android.widget.Toast;

public class CanvasView extends View implements ICanvasView{
    private static int width;
    private static int height;
    private GameManager gameManager;
    private Paint paint;
    private Canvas canvas;
    private Toast toast;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWidthAndHeight(context);
        initPaint();
        gameManager = new GameManager(this, width, height);
    }

    private void initWidthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point); // Получаем правую нижнюю точку экрана.
        width = point.x;
        height = point.y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        gameManager.onDraw();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true); // Сглаживаем края.
        paint.setStyle(Paint.Style.FILL); // Кружочки заполняются цветом.
    }

    @Override
    public void drawCircle(SimpleCircle circle) {
        paint.setColor(circle.getColor());
        canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), paint);
    }

    public void UpdateProgress(int progress){
        paint.setColor(Color.argb(150,255,255,255));
        canvas.drawRect(new Rect(0,0,width,4 * height / 100), paint);
        LinearGradient gradient = new LinearGradient(0, 0, progress, 4 * height / 100,0xFFFFFFF0,
                Color.YELLOW, android.graphics.Shader.TileMode.CLAMP);
        paint.setDither(true);
        paint.setShader(gradient);
        canvas.drawRect(new Rect(0,1 * height / 100,progress * width / 100,3 * height / 100), paint);
        paint.setShader(null);
    }

    @Override
    public void redraw() {
        invalidate();
    }

    @Override
    public void showMessage(String result) {
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), result, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_MOVE)
            gameManager.onTouchEvent(x,y);
        invalidate();
        return true;
    }
}
