package dymmyco.noobs;

import android.content.Intent;
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
    private static File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profielscherm);

        Button login = (Button) findViewById(R.id.buttonDone);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editName);
                String userName = name.getText().toString();

                try {//Writes player name to file
                    File path = getFilesDir();
                    file = new File(path, "ProfileData");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(userName.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    /**
     * -
     * Static method to get the file with user data. Usable in other classes.
     *
     * @return file
     */
    //Lelijk //TODO
    public static File getFile() {
        return file;
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
//                System.exit(0);
                this.finishAffinity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
