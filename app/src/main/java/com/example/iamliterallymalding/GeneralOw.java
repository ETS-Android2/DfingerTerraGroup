package com.example.iamliterallymalding;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.iamliterallymalding.EventHandlers.NavHandler;
import com.example.iamliterallymalding.OpenGL.OpenGLRenderer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneralOw#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralOw extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static GLSurfaceView openGLView;
    private static float [] lidarData;


    public GeneralOw() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneralOw.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralOw newInstance(String param1, String param2) {
        GeneralOw fragment = new GeneralOw();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_general_ow, container, false);

        openGLView = v.findViewById(R.id.generalOVLidar);

        getParentFragmentManager().setFragmentResultListener("lidarDataRequest", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                lidarData = bundle.getFloatArray("lidarData").clone();
                GeneralOw.renderLidar();
            }
        });


        View radarView = v.findViewById(R.id.generalOVRadar);
        radarView.setOnClickListener(new NavHandler(R.id.generalOVRadar, R.id.action_generalOw_to_radarPageFrag));

        View imageView = v.findViewById(R.id.generalOVImage);
        imageView.setOnClickListener(new NavHandler(R.id.generalOVImage, R.id.action_generalOw_to_imagePage));

        v.findViewById(R.id.generalOVLidar).setOnClickListener(new NavHandler(R.id.generalOVLidar, R.id.action_generalOw_to_liadrPageFrag));

        View videoView = v.findViewById(R.id.generalOVVideo);
        videoView.setOnClickListener(new NavHandler(R.id.generalOVVideo, R.id.action_generalOw_to_videoViewFrag));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            openGLView.onResume();
        }
        catch (NullPointerException e){
            renderLidar();
            openGLView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        openGLView.onPause();

        try {
            openGLView.onPause();
        }
        catch (NullPointerException e){
            renderLidar();
            openGLView.onPause();
        }
    }


    protected static void renderLidar(){
        openGLView.setRenderer(new OpenGLRenderer(lidarData));
    }
}