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
    private EditText username, password;


    public LoginHandler(ProgressBar loading, Activity act, EditText username, EditText password){
        this.loading = loading;
        this.act = act;
        this.username = username;
        this.password = password;
    }
    @Override
    public void onClick(View view) {

        loading.setVisibility(View.VISIBLE);

        if (username.getText().toString().equals("admin") && password.getText().toString().equals("123")){ //DEBUG FEATURE. REMOVE BEFORE FINAL
            Navigation.findNavController(view).navigate(R.id.action_logInFrag_to_generalOw);
        }

        else {

            LoginTask loginTask = new LoginTask(username.getText().toString(), password.getText().toString());

            Thread login = new Thread(loginTask);

            if (loading.getVisibility() == View.VISIBLE) {
                login.start();
                loginTask.getOutput().observe((LifecycleOwner) act, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (loginTask.getOutput().getValue() == -1) {
                            loading.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(act, "Something went wrong contacting the database, please try again later", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (loginTask.getOutput().getValue() == 0) {
                            loading.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(act, "invalid creds", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Navigation.findNavController(view).navigate(R.id.action_logInFrag_to_twoFAFrag);
                        }
                    }
                });
            }
        }
    }
}

