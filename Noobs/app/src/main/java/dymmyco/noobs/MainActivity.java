package dymmyco.noobs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

/**
 * This class handles the logic of the first screen, if the user doesn't have a username, he'll be redirected to the profile screen.
 * Created by Dylan on 18-7-2016.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ProfielActivity.getFile() == null) {
            startActivity(new Intent(this, ProfielActivity.class));
        }

        setContentView(R.layout.inlogscherm);

        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(contentExist(ProfielActivity.getFile())) {
                    Intent i = new Intent(getApplicationContext(), SchermTweeActivity.class);
                    startActivity(new Intent(i));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Alerdialog);
                    builder.setTitle("Profiel vereist");
                    builder.setMessage("Vul eerst een profiel in.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(MainActivity.this, ProfielActivity.class));
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
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
            case R.id.action_header:
                startActivity(new Intent(this, ProfielActivity.class));
                return true;
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

    /**
     * Check if the a file has content in it
     * (Can be used for checks)
     * @param file
     * @return
     */
    public boolean contentExist(File file) {
        if(file.exists()) {
            int length = (int) file.length();
            Log.d("Size", "Size =" + length);
            if(length > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
