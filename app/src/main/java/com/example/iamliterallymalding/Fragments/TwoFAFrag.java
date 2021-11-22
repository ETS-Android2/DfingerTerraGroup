package com.example.iamliterallymalding.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iamliterallymalding.EventHandlers.TwoFAHandler;
import com.example.iamliterallymalding.R;
import com.example.iamliterallymalding.Tasks.TwoFATask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoFAFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFAFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TwoFAFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFAFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFAFrag newInstance(String param1, String param2) {
        TwoFAFrag fragment = new TwoFAFrag();
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
        View v = inflater.inflate(R.layout.fragment_two_f_a, container, false);

        Context ctx = v.getContext();

        getParentFragmentManager().setFragmentResultListener("emailRequest", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                System.out.println(result.getString("email"));
                TwoFATask sendCode = new TwoFATask(result.getString("email"));
                Thread sender = new Thread (sendCode);
                sender.start();
                sendCode.getOutput().observe((LifecycleOwner) ctx, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        System.out.println(integer);
                        if (integer != -1)
                            v.findViewById(R.id.TwoFASubmit).setOnClickListener(new TwoFAHandler(integer, v.findViewById(R.id.TwoFACode)));
                    }
                });
            }
        });
        return v;
    }
}