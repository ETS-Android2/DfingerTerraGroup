package com.example.iamliterallymalding.Tasks;



import android.view.View;
import android.widget.ProgressBar;

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
import java.util.concurrent.atomic.AtomicBoolean;


public class LoginTask implements Runnable {

    private String username, password;
    private MutableLiveData<Integer> output = new MutableLiveData<>();


    public LoginTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LiveData<Integer> getOutput() {
        return output;
    }


    @Override
    public void run() {

        MongoDatabase userDb = MongoClients.create("mongodb://192.168.1.64:27017/?serverSelectionTimeoutMS=5000")
                .getDatabase("userData");

        MongoCollection<Document> users = userDb.getCollection("users");


        if (!pingDb(userDb)) {
            output.postValue(-1);
        } else if (attemptLogin(users)) {
            output.postValue(1);
        } else {
            output.postValue(0);
        }

    }


    public Boolean pingDb(MongoDatabase userDb) {
        try {
            Bson pingCommand = new BsonDocument("ping", new BsonInt64(1));
            userDb.runCommand(pingCommand);
            Thread.sleep(5000);
            return true;
        } catch (MongoException | InterruptedException e) {
            return false;
        }
    }

    public Boolean attemptLogin(MongoCollection<Document> users) {
        Document query = new Document("userName", username);

        return users.find(query).first() != null && Objects.requireNonNull(users.find(query).first()).getString("password")
                .equals(password);
    }
}


