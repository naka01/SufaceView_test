package com.example.so.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

public class CircleGraphView extends TextureView implements TextureView.SurfaceTextureListener,Runnable{


    static final long FPS = 30;
    static final long FRAME_TIME = 600 / FPS;
    Thread thread;

    //グラフ描画領域のパディング
    private int leftpadding = 30;
    private int rightpadding = 30;
    private int toppadding = 30;
    private int bottompadding = 30;

    //レイアウトされたViewのサイズ
    private int view_w;
    private int view_h;

    //グラフにするデータセット
    private int percentDate = 0;
    private int percentCalc = 0;

    //円の領域、矩形
    private int leftx,lefty,rad;


    public CircleGraphView(Context context){
        super(context);
        setSurfaceTextureListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int w,
                                          int h) {

        view_h = h;
        view_w = w;

        if(view_h >= view_w){
            rad = view_w-toppadding-bottompadding;
            leftx = toppadding;
            lefty = (view_h-view_w)/2;
        }else{
            rad = view_h-toppadding-bottompadding;
            lefty =toppadding;
            leftx = (view_w-view_h )/2+ leftpadding;
        }


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
        Log.v("log", "update");
    }

    @Override
    public void run() {
        Canvas canvas = null;

        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();

        Paint paint = new Paint();
        Paint bgPaint = new Paint();


        float phase = 0;

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);

        //座標指定
        RectF rect = new RectF(leftx,lefty, leftx + rad, lefty+rad);

        //アニメーション用のループ
        while (thread != null) {

            try {
                loopCount++;

                synchronized (this) {
                    canvas = this.lockCanvas();

                    /**
                     * フレーム描画
                     */

                    //背景の適用
                    canvas.drawPaint(bgPaint);


                    /**
                     * 数字の描画
                     */
                    paint.setColor(Color.BLUE);
                    paint.setTextSize(80);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setAntiAlias(true);
                    float width = rect.width();
                    String text =   String.format("%s",this.percentDate) + "%";
                    int numOfChars = paint.breakText(text,true,width,null);
                    int start = (text.length()-numOfChars)/2;
                    canvas.drawText(text, start, start + numOfChars, rect.centerX(), rect.centerY(), paint);

                    paint.reset();


                    /**
                     * グラフプロット　(Circle)
                     * アニメーション
                     */
                    //グラフラインのスタイル
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
                    paint.setStrokeWidth(30); // 線の太さ
                    paint.setColor(Color.rgb(0, 128, 255)); // 線の色
                    paint.setStrokeJoin(Paint.Join.ROUND);



                    //円弧のみ描写
                    canvas.drawArc(rect, 90,phase,false,paint);

                    phase = phase + 1;

                    paint.reset();



                    this.unlockCanvasAndPost(canvas);

                }

                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);


                if (phase>percentCalc){


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
        Log.v("log", String.format("Last percentDate runの実行終了: %s ", percentDate));
    }


    /**
     * データリストをセット
     * 目標までの割合
     * tunメソッドを止めた後に変更する
     *
     */
    public void setDatasetPer(int perc) {
        this.percentCalc = (int)(360*((float)perc/100));
        this.percentDate = perc;
        Paint paint = new Paint();
        Paint bgPaint = new Paint();

        Canvas canvas = null;

        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);

        synchronized (this) {
            canvas = this.lockCanvas();

            /**
             * フレーム描画
             */

            if(canvas != null) {
                //背景の適用
                canvas.drawPaint(bgPaint);
                //座標指定
                RectF rect = new RectF(leftx, lefty, leftx + rad, lefty + rad);

                /**
                 * 数字の描画
                 */
                paint.setColor(Color.BLUE);
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setAntiAlias(true);
                float width = rect.width();
                String text = String.format("%s", this.percentDate) + "%";
                int numOfChars = paint.breakText(text, true, width, null);
                int start = (text.length() - numOfChars) / 2;
                canvas.drawText(text, start, start + numOfChars, rect.centerX(), rect.centerY(), paint);

                paint.reset();


                /**
                 * グラフプロット　(Circle)
                 * アニメーション
                 */
                //グラフラインのスタイル
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE); // スタイルは線(Stroke)を指定する
                paint.setStrokeWidth(30); // 線の太さ
                paint.setColor(Color.rgb(0, 128, 255)); // 線の色
                paint.setStrokeJoin(Paint.Join.ROUND);


                //円弧のみ描写
                canvas.drawArc(rect, 90, percentCalc, false, paint);


                paint.reset();


                this.unlockCanvasAndPost(canvas);
            }

        }
    }


}
