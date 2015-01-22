package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
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
    SurfaceHolder surfaceHolder;
    Thread thread;
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
    private int target = -1;

    float bx;   //座標
    float by;

    //コンストラクタ
    public SurfaceDrag(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // 半透明を設定
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        this.deg = 0;

        //画像読み込み
        for (int i = 0;i<30;i++) {
            graphicobj = new GraphicObj(context, R.drawable.ic_launcher
                    , (float) MainActivity.percentWidth(2+((i%7)*13))
                    , (float) MainActivity.percentHeight(2+(((int)i/7)*8)));

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

                deg++;

                //画像の描画
                for(int i = 0;i<count;i++) {
                    //画像のアニメーション
                    canvas.save();
                    canvas.rotate(deg
                            , objlist.get(i).getx() + (objlist.get(i).getbmpwid() / 2)
                            , objlist.get(i).gety() + (objlist.get(i).getbmphei() / 2));
                    canvas.drawBitmap(objlist.get(i).getbmp(), objlist.get(i).getx(), objlist.get(i).gety(), bmppaint);
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
                        //配列の入れ替え
                        swap(target);
                        target = 0;

                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                hit = false;
                target = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.v("log",String.format("hit: %s", target));
                if(hit&(target != -1)){

                    diffx = (cx - bx);
                    diffy = (cy - by);
                    objlist.get(target).movebmp(diffx,diffy,screen_width, screen_height);


                    /*if(sidehit(target)) {
                        //diffx = (cx - bx);
                        diffy = (cy - by);
                        objlist.get(target).movebmp(0, diffy);
                    }
                    if(verticalhit(target)) {
                        diffx = (cx - bx);
                        //diffy = (cy - by);
                        objlist.get(target).movebmp(diffx,0);
                    }
                    if(!sidehit(target)&&!verticalhit(target)){
                        diffx = (cx - bx);
                        diffy = (cy - by);
                        objlist.get(target).movebmp(diffx,diffy);
                    }*/
                    //Log.v("log",String.format("hit:side verti %s %s", sidehit(target),verticalhit(target)));
                    /*objlist.get(target).x = objlist.get(target).x + diffx;
                    objlist.get(target).y = objlist.get(target).y + diffy;*/

                    //すべてのオブジェクトを判定
                    //allmove();
                    recursive_move(target);

                }
                //ドラッグしているものと他の判定
                /*for(int i = 0;i<count;i++){
                    if(i != target & target != -1){
                        while(objhit(target,i)){
                            paremove(target,i);
                            for(int j = 0;j<count;j++){
                                if(j != i){
                                    paremove(i,j);
                                }
                            }
                        }

                    }
                }*/

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
            if (Math.pow(objlist.get(target).getx() + (objlist.get(target).getbmpwid() / 2)
                    - objlist.get(other).getx() - (objlist.get(other).getbmpwid() / 2), 2)
                    + Math.pow(objlist.get(target).gety() + (objlist.get(target).getbmphei() / 2)
                    - objlist.get(other).gety() - (objlist.get(other).getbmphei() / 2), 2)
                    < Math.pow((objlist.get(target).radius-25) + (objlist.get(other).radius-25),2)) {
                return true;
            } else {
                return false;
            }
    }

    //壁との接触判定
    private boolean sidehit(int target){
        if(objlist.get(target).getx() + objlist.get(target).getbmpwid() / 2>screen_width
                ||objlist.get(target).getx() -  objlist.get(target).getbmpwid() / 2<0){
            return true;
        }

        return false;
    }

    //壁との接触判定
    private int verticalhit(int target){
        if(objlist.get(target).gety()+45>screen_height){
            return 1;
        }
        if(objlist.get(target).gety()-45<0){
            return 2;
        }

        return 0;

        //return (screen_height-objlist.get(target).gety()<45)||objlist.get(target).gety()<45;
    }

    /*
    すべてのオブジェクトを比較
    移動処理後、接触判定がtrueになったオブジェクトを取りこぼす
     */
    private void allmove(){
        for(int j=count-1;j>=0;j--){
                for (int k = 0; k < count; k++) {
                        if (j != k) {
                            while (objhit(j, k)) {
                                paremove(j,k);

                            }
                        }
                }
        }
    }

    private void paremove(int base ,int move){
        objlist.get(move).movebmp(0.01f * (objlist.get(move).getx() - objlist.get(base).getx())
                , 0.01f * (objlist.get(move).gety() - objlist.get(base).gety()));
    }

    /*
    再帰関数
     */
    private void recursive_move(int tar){
        boolean hit;
        for(int i = 0;i<count;i++){
            hit = false;
            if(i != tar & tar != -1){
                while(objhit(tar,i)){
                    paremove(tar,i);
                    hit = true;
                }
                if(hit) {
                    recursive_move(i);
                }
            }
        }
    }

    private void swap(int target){
        GraphicObj tmp = objlist.get(target);
        objlist.set(target,objlist.get(0));
        objlist.set(0,tmp);
    }



}