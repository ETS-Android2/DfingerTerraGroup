package com.example.iamliterallymalding.DatabasingClasses;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;
import com.example.iamliterallymalding.Tasks.LoginTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class DataInput extends Thread{

    private final EditText username, password;
    private final Context ctx;

    public DataInput (EditText username, EditText password, Context ctx) {
        this.username = username;
        this.password = password;
        this.ctx = ctx;
    }


    public void onClick(View view) {




        //System.out.println(loginTask.getConnStatus());











        /*
        LoginTask netwrk = new LoginTask();

        ExecutorService execute = Executors.newSingleThreadExecutor();

        Future<Boolean> task = execute.submit(netwrk);


        try {
            if (task.get()){
                System.out.println("Db found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //System.out.println(users.countDocuments());




        };

        ExecutorService inputService = Executors.newFixedThreadPool(2);
        Future<Boolean> input = inputService.submit(inputThread);

        try {

            if (input.get()){
                Navigation.findNavController(view).navigate(R.id.action_loadingScreen_to_twoFAFrag);
            }
            else {
                Toast toast = Toast.makeText(ctx, "User doesn't exist or bad creds", Toast.LENGTH_SHORT);
                toast.show();
            }

            inputService.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
         */
    }

}



