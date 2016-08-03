package dymmyco.noobs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import dymmyco.noobs.models.CourseModel;

/**
 * Created by Dylan on 28-7-2016.
 */
public class CijferPopupActivity extends AppCompatActivity implements View.OnClickListener{
    private String cijfer;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        setContentView(R.layout.invoerpopup);

        data = getIntent();

        CourseModel temp = (CourseModel) data.getSerializableExtra("vak");

        Button btnInvoer = (Button) findViewById(R.id.popup_invoerButton);
        Button btnAnnuleren = (Button) findViewById(R.id.popup_annuleerButton);
        TextView header = (TextView) findViewById(R.id.popup_header);
        TextView info = (TextView) findViewById(R.id.popup_info);
        header.setText(temp.name);
        info.setText(temp.info);

        btnInvoer.setOnClickListener(this);
        btnAnnuleren.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popup_invoerButton:
                EditText editCijfer = (EditText) findViewById(R.id.popup_editCijfer);
                cijfer = editCijfer.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("cijfer", cijfer);
                setResult(Activity.RESULT_OK, returnIntent);
                Toast t = Toast.makeText(CijferPopupActivity.this, "Cijfer Opgeslagen", Toast.LENGTH_SHORT);
                t.show();
                finish();
                break;

            case R.id.popup_annuleerButton:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }

    }
}
