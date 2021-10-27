package com.example.iamliterallymalding.DatabasingClasses;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataInput extends Thread implements View.OnClickListener{

    private final EditText username, password;
    private final Context ctx;

    public DataInput (EditText username, EditText password, Context ctx) {
        this.username = username;
        this.password = password;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {

        Callable inputThread = (Callable<Boolean>) () -> { //flex
            try {
                MongoCollection<Document> users = MongoClients.create("mongodb://192.168.1.64:27017")
                        .getDatabase("userData")
                        .getCollection("users");


                Document query = new Document("userName", username.getText().toString());

                return users.find(query).first() != null && Objects.requireNonNull(users.find(query).first()).getString("password")
                        .equals(password.getText().toString());
            }
            catch (MongoTimeoutException e){
                return false;
            }
        };

        ExecutorService inputService = Executors.newFixedThreadPool(2);
        Future<Boolean> input = inputService.submit(inputThread);

        try {
            System.out.println(input.get());
            if (input.get()){
                Navigation.findNavController(view).navigate(R.id.action_logInFrag_to_generalOw);
            }
            else {
                Toast toast = Toast.makeText(ctx, "User doesn't exist or bad creds", Toast.LENGTH_SHORT);
                toast.show();
            }

            inputService.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}



