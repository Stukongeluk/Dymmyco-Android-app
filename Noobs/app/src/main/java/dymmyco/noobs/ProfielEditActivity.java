package dymmyco.noobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class handles the logic of the profile activity edit screen.
 * It's responsible for editing and showing the profile values.
 * Created by Jimmy on 20-7-2016.
 */
public class ProfielEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profielscherm2);

        Button save = (Button) findViewById(R.id.profilesave);
        EditText naam = (EditText) findViewById(R.id.nameview);
        naam.setText(getName(ProfielActivity.getFile()));
        Button cancel = (Button) findViewById(R.id.profilecancel);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.nameview);
                String userName = name.getText().toString();

                try {//Writes player name to file
                    File path = getFilesDir();
                    File file = ProfielActivity.getFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(userName.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Confirmation message
                Toast.makeText(ProfielEditActivity.this,"Opgeslagen", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getName(File file) {
        //lelijk maar moest ff snel snachts ;D//TODO
        int length = (int) file.length();
        byte[] bytes = new byte[length];

        try {//Reads file content
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bytes);//Vies want als er meer info bij het text bestandje komt laat ie niet alleen username zien xD//TODO
    }
}
