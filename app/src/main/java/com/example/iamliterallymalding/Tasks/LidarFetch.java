package com.example.iamliterallymalding.Tasks;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class LidarFetch implements Runnable{

    private MutableLiveData<float[]> output;

    public LidarFetch () {
        this.output = new MutableLiveData<>();
    }

    public LiveData<float[]> getOutput() {return output;}

    @Override
    public void run() {

        MongoCollection lidar = MongoClients.create("mongodb://192.168.1.64:27017/?serverSelectionTimeoutMS=5000")
            .getDatabase("DFingerData").getCollection("lidar");

        ArrayList<Float> outputList = new ArrayList<>();

        for (Document document : (Iterable<Document>) lidar.find()) {
            outputList.add(Float.parseFloat(((String)document.get("Y"))));
            outputList.add(Float.parseFloat(((String)document.get("X"))));
        }

        float [] output = new float[outputList.size()];

        for (int i = 0; i<outputList.size(); i++){
            output[i] = outputList.get(i)*0.8f;
        }

        long timer = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
        while(timer>System.nanoTime()){
            System.out.println("waiting");
        }

        this.output.postValue(output);
    }
}
