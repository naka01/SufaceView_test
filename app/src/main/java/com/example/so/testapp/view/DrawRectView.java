package com.example.so.testapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class DrawRectView extends View {
    private Paint paint;
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;

    public DrawRectView(Context context) {
        this(context, null);
    }

    public DrawRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawRectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 描画される
        canvas.drawRect(100, 100, 200, 200, paint);
        // 描画されない→矩形の左から座標をしていないため？
        canvas.drawRect(50, 50,40, 100,  paint);
        // 描画される
        cacheCanvas.drawRect(300, 300, 400, 400, paint);
        // 描画される→なぜ？
        cacheCanvas.drawRect(250, 250,300, 300,  paint);
        canvas.drawBitmap(cacheBitmap, 0, 0, paint);
    }
}