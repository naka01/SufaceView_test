package com.example.so.testapp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceAnimation extends SurfaceView
        implements Runnable, SurfaceHolder.Callback {

    static final long FPS = 20;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R = 30;
    SurfaceHolder surfaceHolder;
    Thread thread;
    int cx = BALL_R, cy = BALL_R;
    int screen_width, screen_height;

    //点線用エフェクト
    private DashPathEffect lineeffect;
    private PathEffect effect;
    private Paint mDotPaint;
    private float froX , froY,toX,toY,phase = 0,rad = 0;

    public SurfaceAnimation(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.froX = 10;
        this.froY = 10;
        this.toX = 400;
        this.toY = 700;
        this.phase = 0;
        this.rad = 0;

    }

    @Override
    public void run() {

        Canvas canvas = null;
        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);
        // Ball
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        //点線
        mDotPaint = new Paint();
        lineeffect = new DashPathEffect(new float[]{20.0f, 10.0f}, phase);

        mDotPaint.setPathEffect(lineeffect); // 5pixel描いたら5pixel描かないを繰り返す
        mDotPaint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
        mDotPaint.setStrokeWidth(8); // 線の太さ
        mDotPaint.setColor(Color.rgb(0, 128, 255)); // 線の色

        //円
        Paint circle = new Paint();
        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(8);
        circle.setColor(Color.RED);


        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();





        //玉の描画
        /*while(thread != null){

            try{
                loopCount++;
                canvas = surfaceHolder.lockCanvas();


                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                canvas.drawCircle(
                        cx++, cy++, BALL_R,
                        paint);

                surfaceHolder.unlockCanvasAndPost(canvas);

                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if( waitTime > 0 ){
                    Thread.sleep(waitTime);
                }
            }
            catch(Exception e){}
        }*/


        //点線の描画
        while(thread != null) {


            try {
                loopCount++;
                invalidate();
                canvas = surfaceHolder.lockCanvas();

                phase++;
                rad = rad +2;
                Log.v("log", String.format("phase: %s", phase));

                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                canvas.drawCircle(
                        toX, toY, rad,
                        circle);

                lineeffect = new DashPathEffect(new float[]{20.0f, 10.0f}, phase);

                mDotPaint.setPathEffect(lineeffect); // 5pixel描いたら5pixel描かないを繰り返す
                Path mPath = new Path();

                mPath.moveTo(froX, froY);
                mPath.quadTo(froX, froY, toX, toY);

                canvas.drawPath(mPath, mDotPaint);

                lineeffect = null;
                mPath = null;
                mDotPaint.setPathEffect(null);

                surfaceHolder.unlockCanvasAndPost(canvas);


                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if (phase>120){
                    phase = 0;
                }
                if (rad>100){
                    rad = 0;
                }

                if( waitTime > 0 ){
                    Thread.sleep(waitTime);
                }
            } catch (Exception e) {
            }


        }



    }

    public void topointChange(int toX, int toY){
        this.toX = toX;
        this.toY = toY;
    }

    public void frompointChange(int froX,int froY){
        this.froX = froX;
        this.froY = froY;
    }

    @Override
    public void surfaceChanged(
            SurfaceHolder holder,
            int format,
            int width,
            int height) {
        screen_width = width;
        screen_height = height;

        //Log.v("log", String.format("surfaceChanged: %s", width));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

}