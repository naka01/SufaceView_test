package com.example.so.testapp;


import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView  implements SurfaceHolder.Callback{

    private SurfaceHolder m_Holder; // ホルダーへの参照
    private Camera m_Camera;
    public CameraView(Context context) {
        super(context);
        m_Holder= getHolder();
        m_Holder.addCallback(this);
        m_Holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try{
            m_Camera = Camera.open();
            m_Camera.setPreviewDisplay(m_Holder);
        }
        catch(Exception e){

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        m_Camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        m_Camera.setPreviewCallback(null);
        m_Camera.stopPreview();
        m_Camera.release();
        m_Camera = null;
    }
}
