package com.example.iamliterallymalding.OpenGL;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.iamliterallymalding.Shapes.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer { //this class creates a an OpenGLRenderer

    private Triangle trangle; //Declare triangle object (should be renamed to lidar)

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) { //once the view surface is created
        GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f); //render a green background
        trangle = new Triangle(); //instantiate the triangle (lidar) object
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) { //Once a frame is drawn
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT); //I don't know what this does but it won't work without it
        trangle.draw(); //draw the triangle
    }


    public static int loadShader(int type, String shaderCode) { //loading the shader, takes in the type of the shader (vertex or fragment)
        int shader = GLES20.glCreateShader(type); //create the shader object and store it in an integer

        GLES20.glShaderSource(shader, shaderCode); //give the OGL program the shader object and the code which will be used to compile the shader
        GLES20.glCompileShader(shader); //compile the shader

        return shader; //return the compiled shader object
    }
}