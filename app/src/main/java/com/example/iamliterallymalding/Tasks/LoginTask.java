package com.example.iamliterallymalding.Tasks;

import android.widget.EditText;

import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;
import java.util.concurrent.Callable;


public class LoginTask implements Callable {

    private EditText username, password;

    public LoginTask (EditText username, EditText password){
        this.username = username;
        this.password = password;
    }

    @Override
    public Integer call() throws Exception {
        MongoDatabase userDb = MongoClients.create("mongodb://192.168.1.64:27017")
                .getDatabase("userData");

        MongoCollection<Document> users = userDb.getCollection("users");

        if(!pingDb(userDb)){
            return -1;
        }
        else if (attemptLogin(users)){
            return 1;
        }
        else {
            return 0;
        }
    }


    public Boolean pingDb (MongoDatabase userDb){
        try {
            Bson pingCommand = new BsonDocument("ping", new BsonInt64(1));
            userDb.runCommand(pingCommand);
            return true;
        }
        catch (MongoException e) {
            return false;
        }
    }
    public Boolean attemptLogin (MongoCollection<Document> users) {
        Document query = new Document("userName", username.getText().toString());

        return users.find(query).first() != null && Objects.requireNonNull(users.find(query).first()).getString("password")
                .equals(password.getText().toString());
    }
}

