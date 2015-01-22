package com.example.so.testapp;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceAnimation extends SurfaceView
        implements Runnable, SurfaceHolder.Callback {

    static final long FPS = 60;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R = MainActivity.percentHeight(7);
    SurfaceHolder surfaceHolder;
    Thread thread;
    int cx = BALL_R, cy = BALL_R;
    int screen_width, screen_height;

    //点線用エフェクト
    private DashPathEffect lineeffect;
    private Paint mDotPaint;
    private float froX , froY,toX,toY,phase = 0,rad = 0,scale = 0;

    //画像のサイズ
    private int deg;
    private GraphicObj graphicobj;

    private boolean hit = false; //タッチ判定
    float bx;   //座標
    float by;

    public SurfaceAnimation(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.froX = MainActivity.percentWidth(5);
        this.froY = MainActivity.percentHeight(20);
        this.toX = MainActivity.percentWidth(95);
        this.toY = MainActivity.percentHeight(20);
        this.phase = 0;
        this.rad = 0;
        this.scale = 0.1f;
        this.deg = 0;

        //画像読み込み
        graphicobj = new GraphicObj(context,R.drawable.ic_launcher
                ,(float)MainActivity.percentWidth(5), (float)MainActivity.percentHeight(42));


    }

    @Override
    public void run() {

        Canvas canvas = null;
        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        paint.setAntiAlias(true);

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);
        // Ball
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        //画像
        Paint bmppaint = new Paint();
        bmppaint.setAntiAlias(true);
        bmppaint.setFilterBitmap(true);



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



        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();


        //アニメーション用のループ
        while(thread != null) {


            try {
                loopCount++;
                canvas = surfaceHolder.lockCanvas();
                //Log.d("log", String.format("SurfaceView: %s", canvas));
                phase++;
                rad = rad +2;
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

                //画像のアニメーション
                canvas.save();
                canvas.rotate(deg++
                        ,graphicobj.getx()+(graphicobj.getbmpwid()/2)
                        ,graphicobj.gety()+(graphicobj.getbmphei()/2));
                canvas.drawBitmap(graphicobj.getbmp(),graphicobj.getx(),graphicobj.gety(), bmppaint);
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
                mPath.quadTo(MainActivity.percentWidth(15),MainActivity.percentHeight(5)
                        ,MainActivity.percentWidth(25),MainActivity.percentHeight(20));
                mPath.moveTo(MainActivity.percentWidth(25),MainActivity.percentHeight(20));
                mPath.quadTo(MainActivity.percentWidth(45),MainActivity.percentHeight(5)
                        , MainActivity.percentWidth(65),MainActivity.percentHeight(20));
                mPath.moveTo(MainActivity.percentWidth(65),MainActivity.percentHeight(20));

                //Log.v("log", String.format("MainActivity.percentWidth(80): %s", MainActivity.percentWidth(80)));
                mPath.quadTo(MainActivity.percentWidth(80),MainActivity.percentHeight(5)
                        , toX, toY);


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
                if (scale>1){
                    scale = 0;
                }
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
                if(graphicobj.graphicOnTouch(bx,by)){
                    Log.v("log", "hit");
                    hit = true;
                }
                break;
            case MotionEvent.ACTION_UP:

                hit = false;

                break;
            case MotionEvent.ACTION_MOVE:
                if(hit){
                    //Log.v("log", String.format("move: %s", graphicobj.x));
                    graphicobj.movebmp(graphicobj.getx() + (cx - bx),graphicobj.gety() + (cy - by));
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                hit = false;
                break;
        }

        bx = cx;
        by = cy;
        return true;
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
        Log.v("log","surfaceDestroyed");
        thread = null;
    }

    @Override
    protected void onDetachedFromWindow (){
        Log.v("log","onDetachedFromWindow");
    }


}