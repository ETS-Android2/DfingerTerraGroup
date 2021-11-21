package com.example.iamliterallymalding.Tasks;




import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.iamliterallymalding.Encryption.HashPass;
import com.mongodb.MongoException;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;


public class LoginTask extends NetworkTask implements Runnable { //this class checks login information submitted by the user to the login information that exists in the db

    public LoginTask(String username, String password) { //construct task
        super(username, password);
    }

    @Override
    public void run() { //task run method

        MongoCollection<Document> users = super.userDb.getCollection("users"); //saving the required collection in the db

        if (!super.pingDb()) { //try pinging the db
            super.output.postValue(-1);
        } else if (attemptLogin(users)) { //if that works query the db collection for attempted login
            super.output.postValue(1);
        } else {
            super.output.postValue(0); //if query comes up empty output 0
        }
    }

    private Boolean attemptLogin(MongoCollection<Document> users) { // attempt login
        Document query = new Document("userName", super.username); //create new query

        Document queryResult = users.find(query).first();

        if (queryResult != null){
            HashPass validator = new HashPass(password);
            try {
                System.out.println(password);
                System.out.println(validator.validatePassword(queryResult.getString("password")));
                System.out.println(queryResult.getString("password"));
                return validator.validatePassword(queryResult.getString("password"));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}


