package com.example.iamliterallymalding.Tasks;

import android.view.View;

import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;

public class ConcurrentNavigation implements Runnable{

    private View view;

    public ConcurrentNavigation(View view){
        this.view = view;
    }
    @Override
    public void run() {
        Navigation.findNavController(view).navigate(R.id.action_logInFrag_to_loadingScreen);

    }
}
