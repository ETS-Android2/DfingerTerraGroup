package EventHandlers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextSaveHandler implements View.OnClickListener{

    private final int currentView;
    private final String email, password;
    private final Context ctx;

    public TextSaveHandler(int currentView, String email, String password, Context ctx){
        this.currentView = currentView;
        this.email = email;
        this.password = password;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        if (saveDetails()) {
            Toast signupSuccess = Toast.makeText(ctx, "signup success", Toast.LENGTH_SHORT);
            signupSuccess.show();

        }
    }
    private boolean saveDetails (){
        try {
            FileOutputStream fileOut = ctx.openFileOutput("tempLogins.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter (fileOut);
            writer.write(email + ":" + password);
            writer.close();
            System.out.println("works try");
            return true;
        }
        catch (IOException e) {
            System.out.println("there was an error writing to the file :(");
            e.printStackTrace();
            return false;
        }
    }
}
