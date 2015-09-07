package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

public class GraphView extends TextureView implements TextureView.SurfaceTextureListener,Runnable{


    static final long FPS = 60;
    static final long FRAME_TIME = 600 / FPS;
    Thread thread;

    private float scale;

    private int leftpadding = 30;
    private int rightpadding = 30;
    private int toppadding = 30;
    private int bottompadding = 30;

    private int view_w;
    private int view_h;

    public GraphView(Context context){
        super(context);
        setSurfaceTextureListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int w,
                                          int h) {

        view_h = h;
        view_w = w;

        Log.v("log", String.format("onSurfaceTextureAvailable: %s %s", w, h));

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
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int w,
                                            int h) {
        Log.v("log", String.format("onSurfaceTextureSizeChanged: %s %s", w, h));
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

        Path path = new Path();

        paint.setAntiAlias(true);

        int phase = 0;

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);



        /**
         * フレームの描画
         */
        //グラフラインのスタイル
        paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
        paint.setStrokeWidth(3); // 線の太さ
        paint.setColor(Color.rgb(0, 128, 255)); // 線の色

        synchronized (this) {
            canvas = this.lockCanvas();

            //背景の適用
            canvas.drawPaint(bgPaint);

            //ｙ軸の描画
            path.moveTo(leftpadding, toppadding);
            path.rLineTo(0, view_h - toppadding - bottompadding);
            path.rLineTo(view_w - rightpadding -leftpadding, 0);
            path.rLineTo(0,-view_h + toppadding + bottompadding);
            canvas.drawPath(path, paint);

            this.unlockCanvasAndPost(canvas);

        }


        /**
         * グラフの描画
         */
        //グラフラインのスタイル
        paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
        paint.setStrokeWidth(8); // 線の太さ
        paint.setColor(Color.rgb(0, 128, 255)); // 線の色

        // 開始位置

        path.moveTo((float) (phase * Math.PI / 180) * 100, (float) Math.sin(phase * Math.PI / 180) * 100 + 300);
        //アニメーション用のループ
        /*while (thread != null) {

            try {
                loopCount++;

                synchronized (this) {
                    canvas = this.lockCanvas();

                    scale = scale + 0.025f;


                    path.quadTo((float) (phase*Math.PI/180)*100, (float) Math.sin(phase*Math.PI/180)*100+300
                            , (float) ((phase+1)*Math.PI/180)*100, (float) Math.sin((phase+1)*Math.PI/180)*100+300);

                    phase++;

                    canvas.drawPath(path, paint);

                    paint.setPathEffect(null);




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


        }*/
    }


}

