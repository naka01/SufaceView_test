package com.example.so.testapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GraphicObj {
    public float radius;
    public float x;
    public float y;

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
        this.radius = Math.max(bmphei,bmpwid);
    }

    //タッチ可能領域とタッチ判定
    public boolean graphicOnTouch(float cx ,float cy){
        return (this.x-cx)*(this.x-cx) + (this.y-cy)*(this.y-cy)
                < this.radius*this.radius;
    }

    //移動
    public void movebmp(float cx ,float cy){
        x = x + cx;
        y = y + cy;
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

    //リリース
    public void release(){
        this.bmp.recycle();
        this.bmp = null;
    }

}
