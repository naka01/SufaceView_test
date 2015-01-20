package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

public class TextureGraphic extends TextureView implements TextureView.SurfaceTextureListener,Runnable{


    static final long FPS = 60;
    static final long FRAME_TIME = 1000 / FPS;
    Thread thread;


    //画像のデータ
    private int deg;
    private GraphicObj graphicobj;

    public TextureGraphic(Context context){
        super(context);
        setSurfaceTextureListener(this);

        this.deg = 0;

        //画像読み込み
        graphicobj = new GraphicObj(context,R.drawable.ic_launcher,30, 100);
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
        Log.v("log", "TextureView Destroy:");
        synchronized (this) {
            thread.interrupt();
            //thread.stop();
            thread = null;
        /*graphicobj.release();
        graphicobj = null;*/
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

            //画像
            Paint bmppaint = new Paint();
            bmppaint.setAntiAlias(true);
            bmppaint.setFilterBitmap(true);
            try {
                loopCount++;

                synchronized (this) {
                    canvas = this.lockCanvas();

                    //Log.d("log", String.format("TextureView: %s", canvas));
                    //透明色で前回の描画を消す
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    //画像のアニメーション
                    canvas.save();
                    canvas.rotate(deg++
                            , graphicobj.x + (graphicobj.getbmpwid() / 2)
                            , graphicobj.y + (graphicobj.getbmphei() / 2));
                    canvas.drawBitmap(graphicobj.getbmp(), graphicobj.x, graphicobj.y, bmppaint);
                    canvas.restore();

                    this.unlockCanvasAndPost(canvas);
                }

                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if(deg>360){
                    deg = 0;
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

