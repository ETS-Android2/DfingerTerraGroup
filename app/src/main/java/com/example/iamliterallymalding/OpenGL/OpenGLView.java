package com.example.iamliterallymalding.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OpenGLView extends GLSurfaceView {

    public OpenGLView(Context ctx) {
        super(ctx);
        init();
    }
    public OpenGLView(Context ctx, AttributeSet attribs){
        super(ctx, attribs);
        init();
    }
    private void init() {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setRenderer (new OpenGLRenderer());
    }
}
