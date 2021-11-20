package com.example.iamliterallymalding.Tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.iamliterallymalding.Encryption.HashPass;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SignUpTask extends NetworkTask implements Runnable {


    public SignUpTask(String username, String password){
        super(username, password);
    }


    @Override
    public void run() {
        MongoCollection<Document> users = super.userDb.getCollection("users");

        if (!super.pingDb()){
            super.output.postValue(-1);
        }
        else if (attemptPost(users)){
            super.output.postValue(1);
        }
        else {
            super.output.postValue(0);
        }
    }

    private boolean attemptPost(MongoCollection<Document> users){

        HashPass hasher = new HashPass(super.password);

        String hashedPass = "";

        try {
             hashedPass = hasher.generateHash();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }

        try {
            if (users.find(new Document("userName", super.username)).first() == null) {
                users.insertOne(new Document().append("userName", super.username).append("password", hashedPass));
            }
            else {
                return false;
            }
        }
        catch (MongoException e){
            return false;
        }
        return true;
    }

}
