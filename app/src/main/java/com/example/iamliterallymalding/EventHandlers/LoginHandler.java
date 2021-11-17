package com.example.iamliterallymalding.EventHandlers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;
import com.example.iamliterallymalding.Tasks.LoginTask;

public class LoginHandler implements View.OnClickListener{

    private ProgressBar loading;
    private Activity act;
    private EditText username, password;  //declaring all the input vars for the constructor


    public LoginHandler(ProgressBar loading, Activity act, EditText username, EditText password){ //this class handles the login event
        this.loading = loading;
        this.act = act;
        this.username = username;
        this.password = password;
    }
    @Override
    public void onClick(View view) {

        loading.setVisibility(View.VISIBLE); //make the loading spinner visible

        if (username.getText().toString().equals("admin") && password.getText().toString().equals("123")){ //DEBUG FEATURE. REMOVE BEFORE FINAL
            Navigation.findNavController(view).navigate(R.id.action_logInFrag_to_generalOw);
        }

        else {

            LoginTask loginTask = new LoginTask(username.getText().toString(), password.getText().toString()); //creating a new login task object

            Thread login = new Thread(loginTask); //creating a new thread to run the login task

            if (loading.getVisibility() == View.VISIBLE) {  //if the loading spinner is visible
                login.start(); //starting the login thread
                loginTask.getOutput().observe((LifecycleOwner) act, new Observer<Integer>() { //assigning an observer object to the login tasks output method
                    @Override
                    public void onChanged(Integer integer) { //once the output value is updated
                        if (loginTask.getOutput().getValue() == -1) { //if db ping failed show an error toast
                            loading.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(act, "Something went wrong contacting the database, please try again later", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (loginTask.getOutput().getValue() == 0) { //if invalid creds show invalid creds toast
                            loading.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(act, "invalid creds", Toast.LENGTH_SHORT);
                            toast.show();
                        } else { //else navigate to the 2FA frag
                            Navigation.findNavController(view).navigate(R.id.action_logInFrag_to_twoFAFrag);
                        }
                    }
                });
            }
        }
    }
}

