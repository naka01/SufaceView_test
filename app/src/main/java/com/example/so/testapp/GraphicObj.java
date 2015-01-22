package com.example.so.testapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GraphicObj {
    public double radius;
    private float x;
    private float y;

    //画像のサイズ
    private Bitmap bmp;
    private int bmphei;
    private int bmpwid;

    //GraphicObj(contextとリソースid)
    public GraphicObj(Context context,int rid,float x ,float y){
        //画像読み込み
        Resources res = context.getResources();
        bmp = BitmapFactory.decodeResource(res, rid);
        bmphei = bmp.getHeight();
        bmpwid = bmp.getWidth();

        //Touch判定用メンバ
        this.x = x;
        this.y = y;
        //this.radius = Math.max(bmphei,bmpwid);
        this.radius = Math.sqrt(Math.pow(bmphei, 2) + Math.pow(bmpwid, 2))/2;

        Log.v("log", String.format("radius: %s", this.radius));
    }

    //タッチ可能領域とタッチ判定
    public boolean graphicOnTouch(float cx ,float cy){
        return Math.pow((this.x+this.bmpwid/2-cx),2) + Math.pow((this.y+this.bmphei/2-cy),2)
                < Math.pow(this.radius,2);
    }

    //移動
    public void movebmp(float cx ,float cy){

        x = x + cx;
        y = y + cy;
    }

    //移動
    public void movebmp(float cx ,float cy,int w,int h){
        if(x + cx+bmpwid/2-radius>w||x + cx+bmpwid/2-radius<0){
            //x = x + cx;
            y = y + cy;
        }else if(y + cy+bmphei/2-radius>h||y + cy+bmphei/2-radius<0) {
            x = x + cx;
        }else{
            x = x + cx;
            y = y + cy;
        }

    }

    //getter
    public Bitmap getbmp(){
        return bmp;
    }

    public int getbmphei(){
        return bmphei;
    }

    public int getbmpwid(){
        return bmpwid;
    }

    public float getx(){
        return x;
    }

    public float gety(){
        return y;
    }

    //リリース
    public void release(){
        this.bmp.recycle();
        this.bmp = null;
    }

}
