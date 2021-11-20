package com.example.iamliterallymalding.Tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.conversions.Bson;

public class NetworkTask{

    protected String username, password;
    protected MutableLiveData<Integer> output;
    protected MongoDatabase userDb;


    public NetworkTask (String username, String password){
        this.username = username;
        this.password = password;
        this.output = new MutableLiveData<>();
        this.userDb = MongoClients.create("mongodb://192.168.1.64:27017/?serverSelectionTimeoutMS=5000")
                .getDatabase("DFingerData");
    }

    public LiveData<Integer> getOutput() {return output;}

    protected Boolean pingDb() { //method to ping the db
        try {
            Bson pingCommand = new BsonDocument("ping", new BsonInt64(1)); //create ping command
            userDb.runCommand(pingCommand); //run the ping command
            return true; //if that works return true
        } catch (MongoException e) {
            return false; //if mongo exception is throw return false
        }
    }



}
