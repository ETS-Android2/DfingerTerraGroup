package com.example.iamliterallymalding.EventHandlers;

import android.view.View;

import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;


public class NavHandler implements View.OnClickListener{ //this class handles the navigation event

    private final int currentView;
    private final int destination;

    public NavHandler(int currentView, int destination) {
        this.currentView = currentView;
        this.destination = destination;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == currentView)
            Navigation.findNavController(view).navigate(destination);
    }
}
