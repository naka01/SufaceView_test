package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

import java.util.ArrayList;

public class GraphView extends TextureView implements TextureView.SurfaceTextureListener,Runnable{


    static final long FPS = 30;
    static final long FRAME_TIME = 600 / FPS;
    Thread thread;

    //グラフ描画領域のパディング
    private int leftpadding = 80;
    private int rightpadding = 30;
    private int toppadding = 30;
    private int bottompadding = 30;

    //レイアウトされたViewのサイズ
    private int view_w;
    private int view_h;

    //グラフにするデータセット
    private ArrayList<Float> datasetList;
    private int datalistcount;

    //軸のスケール設定
    private float scale_x;
    private float scale_y;
    private float max_y;

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

        Path pathline = new Path();


        float phase = 0;

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);


        // 開始位置
        pathline.moveTo(datasetList.get(0) +  leftpadding
                , datasetList.get(0) - bottompadding + view_h);

        //x軸のスケールをセット
        scale_x = (view_w - rightpadding - leftpadding)/(float)datalistcount;

        //y軸のスケールをセット
        scale_y = view_h/max_y;

        //アニメーション用のループ
        while (thread != null) {

            try {
                loopCount++;

                synchronized (this) {
                    canvas = this.lockCanvas();

                    /**
                     * フレーム描画
                     */
                    //グラフラインのスタイル
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
                    paint.setStrokeWidth(3); // 線の太さ
                    paint.setColor(Color.rgb(255, 128, 0)); // 線の色

                    //背景の適用
                    canvas.drawPaint(bgPaint);

                    //軸の描画
                    path.moveTo(leftpadding, toppadding);
                    path.rLineTo(0, view_h - toppadding - bottompadding);
                    path.rLineTo(view_w - rightpadding - leftpadding, 0);
                    //path.rLineTo(0, -view_h + toppadding + bottompadding);
                    canvas.drawPath(path, paint);
                    paint.reset();

                    //ラベルの描画
                    paint.setAntiAlias(true);
                    paint.setColor(Color.rgb(255, 128, 0)); // 線の色
                    paint.setTextSize(25);
                    float textpos = (paint.getFontMetrics().bottom-paint.getFontMetrics().ascent)/2f;
                    float textXpos = (paint.measureText("Test1"))/2f;

                    canvas.drawText("Test1",0, toppadding + textpos,paint);
                    canvas.drawText("Test2",0, (view_h-bottompadding-toppadding)/2 + textpos,paint);
                    canvas.drawText("Test3",0, view_h-bottompadding-toppadding + textpos,paint);
                    canvas.drawText("Test4",textXpos, view_h,paint);
                    canvas.drawText("Test5",(view_w-leftpadding-rightpadding)/2+textXpos, view_h,paint);
                    canvas.drawText("Test5",view_w-leftpadding-rightpadding+textXpos, view_h,paint);

                    path.reset();
                    paint.reset();
                    /**
                     * グラフプロット
                     * アニメーション
                     */
                    //グラフラインのスタイル
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
                    paint.setStrokeWidth(6); // 線の太さ
                    paint.setColor(Color.rgb(0, 128, 255)); // 線の色
                    paint.setStrokeCap(Paint.Cap.ROUND);
                    paint.setStrokeJoin(Paint.Join.ROUND);


                    pathline.quadTo((float) phase*scale_x +  leftpadding, (float) -datasetList.get((int)phase)*scale_y - bottompadding + view_h
                            , (float) (phase+1)*scale_x +  leftpadding, (float) -datasetList.get((int)(phase + 1))*scale_y - bottompadding + view_h);

                    phase = phase+1;

                    canvas.drawPath(pathline, paint);

                    paint.setPathEffect(null);

                    this.unlockCanvasAndPost(canvas);

                }

                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);


                if (phase>datalistcount -2 ){

                    Log.v("log", String.format("Last  X : %s ", (phase+1)*scale_x));
                    break;
                }

                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }
            } catch (Exception e) {
                //エラー時、unlockする
                //this.unlockCanvasAndPost(canvas);
                break;
            }


        }
    }

    /**
     * データリストをセット
     * @param datasetList
     */
    public void setDatasetList(ArrayList<Float> datasetList) {
        this.datasetList = datasetList;
        this.datalistcount = datasetList.size();
    }

    public void setScale_y(float maxy){
        this.max_y = maxy;
    }
}

