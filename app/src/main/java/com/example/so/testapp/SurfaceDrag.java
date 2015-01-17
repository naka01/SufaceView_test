package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class SurfaceDrag extends SurfaceView
        implements Runnable, SurfaceHolder.Callback {

    static final long FPS = 60;
    static final long FRAME_TIME = 1000 / FPS;
    //static final int BALL_R = MainActivity.percentHeight(7);
    SurfaceHolder surfaceHolder;
    Thread thread;
    //int cx = BALL_R, cy = BALL_R;
    int screen_width, screen_height;

    //画像のサイズ
    private int deg;
    private GraphicObj graphicobj;

    List<GraphicObj> objlist = new ArrayList<GraphicObj>();
    private int count;


    private boolean hit = false; //タッチ判定

    //ドラッグ時の変位
    private float diffx = 0,diffy = 0;

    //タッチ中のobj
    private int target = 0;

    float bx;   //座標
    float by;

    //コンストラクタ
    public SurfaceDrag(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.deg = 0;

        //画像読み込み
        for (int i = 0;i<5;i++) {
            graphicobj = new GraphicObj(context, R.drawable.ic_launcher
                    , (float) MainActivity.percentWidth(5+(i*15)), (float) MainActivity.percentHeight(42));

            objlist.add(graphicobj);
        }


    }

    @Override
    public void run() {

        Canvas canvas = null;

        //画像
        Paint bmppaint = new Paint();
        bmppaint.setAntiAlias(true);
        bmppaint.setFilterBitmap(true);

        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();

        //画像の個数
        count = objlist.size();

        //アニメーション用のループ
        while(thread != null) {


            try {
                loopCount++;
                canvas = surfaceHolder.lockCanvas();


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

                surfaceHolder.unlockCanvasAndPost(canvas);


                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if(deg>360){
                    deg = 0;
                }

                if( waitTime > 0 ){
                    Thread.sleep(waitTime);
                }
            } catch (Exception e) {
            }


        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //現在のタッチ座標
        float cx = event.getX();
        float cy = event.getY();



        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                bx = cx;
                by = cy;
                for(int i = 0;i<count;i++) {
                    if (objlist.get(i).graphicOnTouch(bx, by)) {
                        Log.v("log",String.format("hit: %s", i));
                        target = i;
                        hit = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                hit = false;
                target = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if(hit&(target != -1)){
                    diffx = (cx - bx);
                    diffy = (cy - by);
                    objlist.get(target).x = objlist.get(target).x + diffx;
                    objlist.get(target).y = objlist.get(target).y + diffy;
                }
                for(int i = 0;i<count;i++){
                    if(i != target & target != -1){
                        while(objhit(target,i)){

                            objlist.get(i).movebmp(0.001f*(objlist.get(i).x-objlist.get(target).x-diffx)
                                    ,0.001f*(objlist.get(i).y-objlist.get(target).y-diffy));
                        }
                    }
                }


                break;
            case MotionEvent.ACTION_CANCEL:
                target = -1;
                hit = false;
                break;
        }

        bx = cx;
        by = cy;
        return true;
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
}