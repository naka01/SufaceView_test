package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;


public class TextureAnimation extends TextureView  implements TextureView.SurfaceTextureListener,Runnable{


    static final long FPS = 60;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R = MainActivity.percentHeight(7);
    Thread thread;

    private float scale;

    public TextureAnimation(Context context){
        super(context);
        setSurfaceTextureListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
        synchronized (this) {
            thread = new Thread(this);
            thread.start();
        }
    }
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (this) {
            thread.interrupt();
            thread = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {
    }
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void run() {
        Canvas canvas = null;


        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();


        //アニメーション用のループ
        while (thread != null) {
            Paint paint = new Paint();
            Paint bgPaint = new Paint();

            paint.setAntiAlias(true);

            // Background
            bgPaint.setStyle(Paint.Style.FILL);
            bgPaint.setColor(Color.WHITE);
            // Ball
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);


                try {
                    loopCount++;

                    synchronized (this) {
                        canvas = this.lockCanvas();

                        scale = scale + 0.025f;

                        //透明色で前回の描画を消す
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        //玉の描画
                        canvas.save();
                        canvas.scale(scale, scale, MainActivity.percentHeight(20), MainActivity.percentHeight(30));
                        canvas.drawCircle(
                                MainActivity.percentHeight(20), MainActivity.percentHeight(30), BALL_R,
                                paint);
                        canvas.restore();


                        this.unlockCanvasAndPost(canvas);
                    }

                    waitTime = (loopCount * FRAME_TIME)
                            - (System.currentTimeMillis() - startTime);

                    if (scale > 1) {
                        scale = 0;
                    }


                    if (waitTime > 0) {
                        Thread.sleep(waitTime);
                    }
                } catch (Exception e) {
                    break;
                }


        }
    }


}

