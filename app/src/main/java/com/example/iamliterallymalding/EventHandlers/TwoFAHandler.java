package com.example.iamliterallymalding.EventHandlers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;
import com.example.iamliterallymalding.Tasks.TwoFATask;

public class TwoFAHandler implements View.OnClickListener{

    private int code;
    private EditText submitted;

    public TwoFAHandler(Integer integer, EditText submitted) {
        code = integer;
        this.submitted = submitted;
    }

    @Override
    public void onClick(View view) {
        System.out.println("ass");
        if (code == Integer.parseInt(submitted.getText().toString())){
            Navigation.findNavController(view).navigate(R.id.action_twoFAFrag_to_loadingScreen);
        }
    }
}
