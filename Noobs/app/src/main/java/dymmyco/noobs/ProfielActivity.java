package dymmyco.noobs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class handles the logic of the profile activity screen. It's responsible for making a file with user data.
 * Created by Dylan on 20-7-2016.
 */
public class ProfielActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.profielscherm);

        Button login = (Button) findViewById(R.id.buttonDone);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeToFile();
                //Instead of making new activity, back to the previous activity ;D
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                // help stuff
                return true;

            case R.id.action_settings:
                // settings stuff
                return true;

            case R.id.action_close:
                this.finishAffinity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void writeToFile() {
        EditText name = (EditText) findViewById(R.id.editName);
        String userName = name.getText().toString();

        try {
            FileOutputStream fos = openFileOutput("ProfileData", Context.MODE_PRIVATE);
            fos.write(userName.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
