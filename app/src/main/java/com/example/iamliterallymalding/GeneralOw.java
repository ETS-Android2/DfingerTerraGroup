package com.example.iamliterallymalding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneralOw#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralOw extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

        ImageView radarClick = v.findViewById(R.id.generalOVRadar);
        radarClick.setOnClickListener(this);

        ImageView imageClick = v.findViewById(R.id.generalOVImage);
        imageClick.setOnClickListener(this);

        VideoView videoClick = v.findViewById(R.id.generalOVVideo);
        videoClick.setOnClickListener(this);

        VideoView lidarClick = v.findViewById(R.id.generalOVLidar);
        lidarClick.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.generalOVRadar){
            Navigation.findNavController(v).navigate(R.id.action_generalOw_to_radarPageFrag);
        }
        else if(v.getId() == R.id.generalOVImage){
            Navigation.findNavController(v).navigate(R.id.action_generalOw_to_imagePage);
        }
        else if(v.getId() == R.id.generalOVVideo){
            Navigation.findNavController(v).navigate(R.id.action_generalOw_to_videoViewFrag);
        }
        else if(v.getId() == R.id.generalOVLidar){
            Navigation.findNavController(v).navigate(R.id.action_generalOw_to_liadrPageFrag);
        }
    }
}