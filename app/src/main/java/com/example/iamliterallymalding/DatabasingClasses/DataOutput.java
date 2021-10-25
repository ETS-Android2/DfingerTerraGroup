package com.example.iamliterallymalding.DatabasingClasses;



import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataOutput implements View.OnClickListener {

    private final EditText username, password;
    private final Context ctx;

    public DataOutput (EditText username, EditText password, Context ctx){
        this.username = username;
        this.password = password;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) { //ngl chief I'm not 100% sure how this shit works but it does so WHATEVER

        Callable outputToDB = (Callable<Boolean>) () -> { //flex
            MongoCollection<Document> users = MongoClients.create("mongodb://192.168.1.64:27017")
                    .getDatabase("userData")
                    .getCollection("users");

            if (users.find(new Document ("userName", username.getText().toString())).first() == null) {
                users.insertOne(new Document("userName", username.getText().toString()).append("password", password.getText().toString()));
                return true;
            }
            return false;
        };

        ExecutorService outputService = Executors.newFixedThreadPool(2);

        Future<Boolean> output = outputService.submit(outputToDB);

        try {
            if (output.get()){
                Toast toast = Toast.makeText(ctx, "Your account has been created", Toast.LENGTH_SHORT);
                toast.show();
                Navigation.findNavController(view).navigate(R.id.action_signUp_to_logInFrag);
            }
            else{
                Toast toast = Toast.makeText(ctx, "That user already exists", Toast.LENGTH_SHORT);
                toast.show();
            }

            outputService.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }


}
