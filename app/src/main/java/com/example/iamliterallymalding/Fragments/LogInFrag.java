package com.example.iamliterallymalding.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iamliterallymalding.EventHandlers.LoginHandler;
import com.example.iamliterallymalding.EventHandlers.NavHandler;
import com.example.iamliterallymalding.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFrag extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText username, password;

    public LogInFrag() {
        // Required empty public constructor
    }

    public static LogInFrag newInstance(String param1, String param2) {
        LogInFrag fragment = new LogInFrag();
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

        View v = inflater.inflate(R.layout.fragment_log_in, container, false);

        EditText attemptedLogin = v.findViewById(R.id.usernameInput);
        EditText attemptedPass = v.findViewById(R.id.passInput);

        Button logInBtn = v.findViewById(R.id.logIn);
        TextView signupBtn = v.findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(new NavHandler(R.id.signupBtn, R.id.action_logInFrag_to_signUp));
        logInBtn.setOnClickListener(new LoginHandler(v.findViewById(R.id.progressSpinner), this.getActivity(), attemptedLogin, attemptedPass));

        return v;
    }
}