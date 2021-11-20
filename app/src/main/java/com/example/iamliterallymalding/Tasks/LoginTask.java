package com.example.iamliterallymalding.Tasks;




import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mongodb.MongoException;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;


public class LoginTask implements Runnable { //this class checks login information submitted by the user to the login information that exists in the db

    private String username, password; //declaring username and password strings
    private MutableLiveData<Integer> output = new MutableLiveData<>(); //declaring the output variable


    public LoginTask(String username, String password) { //construct task
        this.username = username;
        this.password = password;
    }

    public LiveData<Integer> getOutput() {
        return output;
    } //method to return the output


    @Override
    public void run() { //task run method

        MongoDatabase userDb = MongoClients.create("mongodb://192.168.1.64:27017/?serverSelectionTimeoutMS=5000")
                .getDatabase("DFingerData"); //tell the method where to connect to the db and when to stop trying

        MongoCollection<Document> users = userDb.getCollection("users"); //saving the required collection in the db


        if (!pingDb(userDb)) { //try pinging the db
            output.postValue(-1);
        } else if (attemptLogin(users)) { //if that works query the db collection for attempted login
            output.postValue(1);
        } else {
            output.postValue(0); //if query comes up empty output 0
        }

    }


    public Boolean pingDb(MongoDatabase userDb) { //method to ping the db
        try {
            Bson pingCommand = new BsonDocument("ping", new BsonInt64(1)); //create ping command
            userDb.runCommand(pingCommand); //run the ping command
            return true; //if that works return true
        } catch (MongoException e) {
            return false; //if mongo exception is throw return false
        }
    }

    public Boolean attemptLogin(MongoCollection<Document> users) { // attempt login
        Document query = new Document("userName", username); //create new query

        return users.find(query).first() != null && Objects.requireNonNull(users.find(query).first()).getString("password")
                .equals(password); //boolean algebra to check login
    }
}


