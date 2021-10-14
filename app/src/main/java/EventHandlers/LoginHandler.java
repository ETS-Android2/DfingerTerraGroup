package EventHandlers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LoginHandler implements View.OnClickListener{

    private final int currentView, destination;
    private final String attemptedLogin, attemptedPass;
    private final Context ctx;

    public LoginHandler(int currentView, int destination, String attemptedLogin, String attemptedPass, Context ctx) {
        this.currentView = currentView;
        this.destination = destination;
        this.attemptedLogin = attemptedLogin;
        this.attemptedPass = attemptedPass;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == currentView){
            System.out.println(readFile());
            if (readFile().get(attemptedLogin) == attemptedPass){
                Navigation.findNavController(view).navigate(destination);
            }
            else {
                System.out.println("too bad pass bad");
            }
        }
    }

    private HashMap<String, String> readFile(){

        String readData = "";

        try {
            InputStream inputStream = ctx.openFileInput("tempLogins.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                readData = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }

        String[] loginData = readData.split(":");

        HashMap<String, String> output = new HashMap<>();

        output.put(loginData[0], loginData[1]);

        return output;

    }


}
