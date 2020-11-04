package dev.victorman.scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MovableCanvasView extends View {
    private final Context context;
    private float baseWidth;
    private float baseHeight;
    private float width;
    private float height;
    private Scale scale;
    private RectF viewFieldRect;
    private List<ScaledDrawable> drawablesList;
    private float prevx;
    private float prevy;

    public MovableCanvasView(Context context, float baseCanvasWidth, float baseCanvasHeight) {
        super(context);
        this.context = context;
        this.baseWidth = baseCanvasWidth;
        this.baseHeight = baseCanvasHeight;
        width = baseCanvasWidth;
        height = baseCanvasHeight;
        scale = new Scale(1f, 1f);
        drawablesList = new ArrayList<ScaledDrawable>();
        viewFieldRect = new RectF(0, 0, getWidth(), getHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(viewFieldRect.width() == 0) {
            viewFieldRect.set(0, 0, getWidth(), getHeight());
        }

        for(Drawable d: drawablesList) {
            d.draw(canvas);
        }

    }


    public void addDrawable(ScaledDrawable drawable) {
        drawable.viewFieldRect = viewFieldRect;
        drawable.scale = scale;
        drawablesList.add(drawable);
    }

    public void fingerMove(float dx, float dy) {

        if(viewFieldRect.left + dx < 0)
            dx = 0;
        if(viewFieldRect.top + dy < 0)
            dy = 0;
        if(viewFieldRect.right + dx > width) {
            viewFieldRect.right = width;
            if(dx > 0)
                dx = 0;
        }
        if(viewFieldRect.bottom + dy > height) {
            viewFieldRect.bottom = height;
            if(dy > 0)
                dy = 0;
        }
        viewFieldRect.set((int)(viewFieldRect.left + dx),
                (int)(viewFieldRect.top + dy),
                (int)(viewFieldRect.right + dx),
                (int)(viewFieldRect.bottom + dy));

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void fingerDown(float x, float y) {
        prevx = x;
        prevy = y;
    }

    public void scaleCanvas(float scaleFactor) {
        scale.setHeightScale(scaleFactor);
        scale.setWidthScale(scaleFactor);
        width = width * scale.getWidthScale();
        height = height * scale.getHeightScale();
        Log.i("scale", "scaled: " + width + " " + height);
        Log.i("scale", "screen: " + getWidth() + " " + getHeight());
    }
}
