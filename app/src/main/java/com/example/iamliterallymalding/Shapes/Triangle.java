package com.example.iamliterallymalding.Shapes;

import android.opengl.GLES20;

import com.example.iamliterallymalding.OpenGL.OpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private FloatBuffer vertexBuffer;
    private final int shaderProgram;

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "void main() {" +
            "   gl_Position = vPosition;" +
            "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "   gl_FragColor = vColor;" +
            "}";

    static float[] triangleCoords = {
            0.0f, 0.5f, 0.0f,
            -0.5f,-0.3f, 0.0f,
            0.5f, -0.3f, 0.0f
    };

    static final int coordsInVertex = 3, vertexCount = triangleCoords.length / coordsInVertex, vertexStride = coordsInVertex * 4;

    private int positionHandle, colorHandle;

    float color[] = {1f, 0f, 0f, 1f};

    public Triangle () {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put (triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);
    }

    public void draw () {
        GLES20.glUseProgram(shaderProgram);
        positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, coordsInVertex, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
