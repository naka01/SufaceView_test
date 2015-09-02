package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;


public class TextureAnimation extends TextureView  implements TextureView.SurfaceTextureListener,Runnable{


    static final long FPS = 60;
    static final long FRAME_TIME = 600 / FPS;
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


        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        paint.setAntiAlias(true);

        int phase = 0;
        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);
        // Ball
            /*paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);*/

        //line
        paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
        paint.setStrokeWidth(8); // 線の太さ
        paint.setColor(Color.rgb(0, 128, 255)); // 線の色

        // 開始位置
        Path path = new Path();
        path.moveTo((float) (phase * Math.PI / 180) * 100, (float) Math.sin(phase * Math.PI / 180) * 100 + 300);
        //アニメーション用のループ
        while (thread != null) {


                try {
                    loopCount++;



                    synchronized (this) {
                        canvas = this.lockCanvas();

                        scale = scale + 0.025f;

                        //透明色で前回の描画を消す
                        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        //グラフ線の描画
                        /*lineeffect = new DashPathEffect(new float[]{20.0f, 10.0f},3);

                        paint.setPathEffect(lineeffect); // 5pixel描いたら5pixel描かないを繰り返す*/


                        // 開始位置
                        //path.moveTo(0, 0);

                        // 線の描画

                        /*for(int i=0 ; i<719; i++){
                            path.quadTo((float) (i*Math.PI/180)*100, (float) Math.sin(i*Math.PI/180)*100+300
                                    , (float) ((i+1)*Math.PI/180)*100, (float) Math.sin((i+1)*Math.PI/180)*100+300);
                        }*/



                        path.quadTo((float) (phase*Math.PI/180)*100, (float) Math.sin(phase*Math.PI/180)*100+300
                                , (float) ((phase+1)*Math.PI/180)*100, (float) Math.sin((phase+1)*Math.PI/180)*100+300);

                        phase++;

                        canvas.drawPath(path, paint);

                        paint.setPathEffect(null);



                        
                        //玉の描画
                        /*canvas.save();
                        canvas.scale(scale, scale, MainActivity.percentHeight(20), MainActivity.percentHeight(30));
                        canvas.drawCircle(
                                MainActivity.percentHeight(20), MainActivity.percentHeight(30), BALL_R,
                                paint);
                        canvas.restore();*/


                        this.unlockCanvasAndPost(canvas);

                    }

                    waitTime = (loopCount * FRAME_TIME)
                            - (System.currentTimeMillis() - startTime);


                    if (phase>359){
                        break;
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

