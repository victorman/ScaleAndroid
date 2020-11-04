package dev.victorman.scale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();


        final MovableCanvasView canvasView = new MovableCanvasView(context, 2000f,4000f);
        canvasView.setOnTouchListener(new MoveViewFrameTouchListener(context));

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        ScaledDrawable drawable = new CircleDrawable(paint);
        drawable.setBounds(new Rect(200, 200, 300, 300));
        ScaledDrawable drawable2 = new CircleDrawable(paint);
        drawable2.setBounds(new Rect(1580, 3580, 1680, 3680));

        ScaledDrawable drawable3 = new CircleDrawable(paint);
        drawable3.setBounds(new Rect(880, 1580, 1080, 1780));
        canvasView.addDrawable(drawable);
        canvasView.addDrawable(drawable2);
        canvasView.addDrawable(drawable3);

        canvasView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        setContentView(canvasView);

        Runnable runnable = new Runnable() {
            long elapsedTime = 0L;
            long fps = 20L;
            long frameDuration = 1000L / fps;
            long lastTime = 0L;
            @Override
            public void run() {
                while(true) {
                    elapsedTime = System.currentTimeMillis() - lastTime;
                    if (elapsedTime > frameDuration) {
                        canvasView.invalidate();
                        lastTime = System.currentTimeMillis();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);


        thread.start();
    }
}