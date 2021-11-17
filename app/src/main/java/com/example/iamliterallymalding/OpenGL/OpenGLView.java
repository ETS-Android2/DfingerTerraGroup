package com.example.iamliterallymalding.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OpenGLView extends GLSurfaceView { //creating the custom OGL view

    public OpenGLView(Context ctx) { //give the super class the context and run the init method
        super(ctx);
        init();
    }
    public OpenGLView(Context ctx, AttributeSet attribs){ //Alternate constructor that gives the view various attributes
        super(ctx, attribs);
        init();
    }
    private void init() { //initialise the view
        setEGLContextClientVersion(2); //set the EGL context client version, in this case GLES 2.0
        setPreserveEGLContextOnPause(true); //No idea what this does
        setRenderer (new OpenGLRenderer()); //creates a new renderer object and sets it as this view's renderer
    }
}
