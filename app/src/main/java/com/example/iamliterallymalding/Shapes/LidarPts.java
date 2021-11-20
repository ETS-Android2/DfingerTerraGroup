package com.example.iamliterallymalding.Shapes;

import android.opengl.GLES20;
import android.opengl.GLES10;

import com.example.iamliterallymalding.OpenGL.OpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class LidarPts {
    private FloatBuffer vertexBuffer;
    private final int shaderProgram; //declaring the vertex buffer and shader program objects


    private final String vertexShaderCode = //writing the vertex shader code in GLSL as a string (weird, but works)
            "attribute vec4 vPosition;" +
            "void main() {" +
            "   gl_Position = vPosition;" +
            "   gl_PointSize = 5.0;"+
            "}";
    private final String fragmentShaderCode = //same as above except for fragment shader
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "   gl_FragColor = vColor;" +
            "}";

    float[] lidarCoords; // array of floating points for vertex coordinates
    
    

    final int coordsInVertex = 2, vertexCount, vertexStride = coordsInVertex * 4;
    //various integers to keep track of vertex information

    private int positionHandle, colorHandle; //idk what this does

    float color[] = {1f, 0f, 0f, 1f}; //sets the color for the vertices

    public LidarPts(float [] lidarCoords) { //constructing triangle (lidar) object
        this.lidarCoords = lidarCoords;
        this.vertexCount = lidarCoords.length / coordsInVertex;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(lidarCoords.length*4); //directly allocating four bytes of data for each coordinate of each vertex
        byteBuffer.order(ByteOrder.nativeOrder()); //ordering the bytes in the buffer

        vertexBuffer = byteBuffer.asFloatBuffer(); //saving the byte buffer as a floating point buffer
        vertexBuffer.put (lidarCoords); //putting the vertex coordinates in the buffer
        vertexBuffer.position(0); //setting the position of the buffer at the start

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode); //loading the vertex shader
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);//loading the fragment shader

        shaderProgram = GLES20.glCreateProgram(); //creating the shader program
        GLES20.glAttachShader(shaderProgram, vertexShader); //attaching the shaders to the program
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram); //linking the program to the app
    }

    public void draw () {
        GLES20.glUseProgram(shaderProgram); //using the shader program
        positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition"); //assigning the vertex location handle to the vertex shader
        GLES20.glEnableVertexAttribArray(positionHandle); //enabling the java array to be used as a vertex location array (I think)
        GLES20.glVertexAttribPointer(positionHandle, coordsInVertex, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        //assigning the vertex coordinates to the position handle (I think)
        colorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor"); //assigning the colour handle fragment shader
        GLES20.glUniform4fv(colorHandle, 1, color, 0); //setting the colour
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount); //drawing all the vertices
        GLES20.glDisableVertexAttribArray(positionHandle); //disabling modification of vertex locations
    }
}
