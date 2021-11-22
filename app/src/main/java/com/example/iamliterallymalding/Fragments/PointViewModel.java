package com.example.iamliterallymalding.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PointViewModel extends ViewModel {
    private float[] lidarPts;

    public float[] getPts() {
        return lidarPts;
    }
    public void setLidarPts(float[] pts){
        lidarPts = pts;
    }
}
