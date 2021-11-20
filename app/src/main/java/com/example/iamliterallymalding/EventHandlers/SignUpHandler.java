package com.example.iamliterallymalding.EventHandlers;



import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iamliterallymalding.R;
import com.example.iamliterallymalding.Tasks.SignUpTask;

public class SignUpHandler implements View.OnClickListener{

    private ProgressBar loading;
    private Context ctx;
    private EditText username, password;


    public SignUpHandler(ProgressBar loading, Context ctx, EditText username, EditText password){
       this.loading = loading;
       this.ctx = ctx;
       this.username = username;
       this.password = password;
    }


    @Override
    public void onClick(View v) {

        loading.setVisibility(View.VISIBLE);

        SignUpTask signUpTask = new SignUpTask(username.getText().toString(), password.getText().toString());
        Thread signUp = new Thread(signUpTask);

        if (loading.getVisibility() == View.VISIBLE){
            signUp.start();
            signUpTask.getOutput().observe((LifecycleOwner) ctx, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (signUpTask.getOutput().getValue() == -1){
                        loading.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(ctx,"Something went wrong contacting the database, please try later", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (signUpTask.getOutput().getValue() == 0){
                        loading.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(ctx, "User Already exists", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(ctx, "User successfully created", Toast.LENGTH_SHORT);
                        toast.show();
                        Navigation.findNavController(v).navigate(R.id.action_signUp_to_logInFrag);
                    }
                }
            });
        }
    }
}
