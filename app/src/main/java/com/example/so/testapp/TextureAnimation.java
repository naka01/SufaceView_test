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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.TextureView;

import java.util.ArrayList;
import java.util.List;

public class TextureAnimation extends TextureView  implements TextureView.SurfaceTextureListener,Runnable{


    List<GraphicObj> objlist = new ArrayList<GraphicObj>();
    private int count;

    //ドラッグ時の変位
    private float diffx = 0,diffy = 0;

    //タッチ中のobj
    private int target = 0;

    float bx;   //座標
    float by;

    static final long FPS = 20;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R = MainActivity.percentHeight(7);
    Thread thread;

    //点線用エフェクト
    private DashPathEffect lineeffect;
    private Paint mDotPaint;
    private float froX , froY,toX,toY,phase = 0,rad = 0,scale = 0;


    private boolean hit = false; //タッチ判定

    public TextureAnimation(Context context){
        super(context);
        setSurfaceTextureListener(this);
        this.froX = MainActivity.percentWidth(5);
        this.froY = MainActivity.percentHeight(20);
        this.toX = MainActivity.percentWidth(95);
        this.toY = MainActivity.percentHeight(20);
        this.phase = 0;
        this.rad = 0;
        this.scale = 0.1f;
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
        thread=new Thread(this);
        thread.start();
    }
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        thread=null;
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

        //画像の個数
        count = objlist.size();

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

            //点線
            mDotPaint = new Paint();
            mDotPaint.setAntiAlias(true);
            lineeffect = new DashPathEffect(new float[]{20.0f, 10.0f}, phase);

            mDotPaint.setPathEffect(lineeffect); // 5pixel描いたら5pixel描かないを繰り返す
            mDotPaint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
            mDotPaint.setStrokeWidth(8); // 線の太さ
            mDotPaint.setColor(Color.rgb(0, 128, 255)); // 線の色

            //円
            Paint circle = new Paint();
            circle.setAntiAlias(true);
            circle.setStyle(Paint.Style.STROKE);
            circle.setStrokeWidth(8);
            circle.setColor(Color.RED);


            //アニメーション用のループ
            while (thread != null) {


                try {
                    loopCount++;
                    canvas = this.lockCanvas();

                    phase++;
                    rad = rad + 2;
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


                    //円の描画
                    canvas.drawCircle(
                            toX, toY, rad,
                            circle);

                    //点線の描画
                    lineeffect = new DashPathEffect(new float[]{20.0f, 10.0f}, phase);

                    mDotPaint.setPathEffect(lineeffect); // 5pixel描いたら5pixel描かないを繰り返す
                    Path mPath = new Path();

                    mPath.moveTo(froX, froY);
                    mPath.quadTo(MainActivity.percentWidth(15), MainActivity.percentHeight(5)
                            , MainActivity.percentWidth(25), MainActivity.percentHeight(20));
                    mPath.moveTo(MainActivity.percentWidth(25), MainActivity.percentHeight(20));
                    mPath.quadTo(MainActivity.percentWidth(45), MainActivity.percentHeight(5)
                            , MainActivity.percentWidth(65), MainActivity.percentHeight(20));
                    mPath.moveTo(MainActivity.percentWidth(65), MainActivity.percentHeight(20));

                    //Log.v("log", String.format("MainActivity.percentWidth(80): %s", MainActivity.percentWidth(80)));
                    mPath.quadTo(MainActivity.percentWidth(80), MainActivity.percentHeight(5)
                            , toX, toY);


                    canvas.drawPath(mPath, mDotPaint);

                    lineeffect = null;
                    mPath = null;
                    mDotPaint.setPathEffect(null);

                    this.unlockCanvasAndPost(canvas);


                    waitTime = (loopCount * FRAME_TIME)
                            - (System.currentTimeMillis() - startTime);

                    if (phase > 120) {
                        phase = 0;
                    }
                    if (rad > 100) {
                        rad = 0;
                    }
                    if (scale > 1) {
                        scale = 0;
                    }

            /*try {
                loopCount++;
                canvas = this.lockCanvas();


                //透明色で前回の描画を消す
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                //画像の描画
                for(int i = 0;i<count;i++) {
                    //画像のアニメーション
                    canvas.save();
                    canvas.rotate(deg++
                            , objlist.get(i).x + (objlist.get(i).getbmpwid() / 2)
                            , objlist.get(i).y + (objlist.get(i).getbmphei() / 2));
                    canvas.drawBitmap(objlist.get(i).getbmp(), objlist.get(i).x, objlist.get(i).y, bmppaint);
                    canvas.restore();
                }

                this.unlockCanvasAndPost(canvas);


                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if(deg>360){
                    deg = 0;
                }*/

                    if (waitTime > 0) {
                        Thread.sleep(waitTime);
                    }
                } catch (Exception e) {
                }


            }


        }
    }
    //接触判定
    private boolean objhit(int target,int other){
        if (Math.pow(objlist.get(target).x + (objlist.get(target).getbmpwid() / 2)
                - objlist.get(other).x + (objlist.get(other).getbmpwid() / 2), 2)
                + Math.pow(objlist.get(target).y + (objlist.get(target).getbmpwid() / 2)
                - objlist.get(other).y + (objlist.get(other).getbmpwid() / 2), 2)
                <  Math.pow(objlist.get(target).radius + objlist.get(other).radius,2)) {
            return true;
        } else {
            return false;
        }
    }

    protected void MyDraw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 100, 100, paint);
    }
}

