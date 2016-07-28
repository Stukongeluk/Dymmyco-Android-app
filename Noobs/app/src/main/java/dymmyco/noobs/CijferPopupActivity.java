package dymmyco.noobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Dylan on 28-7-2016.
 */
public class CijferPopupActivity extends AppCompatActivity {
    private static String cijfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.invoerpopup);

        Button btnInvoer = (Button) findViewById(R.id.popup_invoerButton);

        btnInvoer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editCijfer = (EditText) findViewById(R.id.popup_editCijfer);
                cijfer = editCijfer.getText().toString();
                Intent i = new Intent(getApplicationContext(), InvoerActivity.class);
                startActivity(new Intent(i));
            }
        });
    }
    public static String getCijfer() {
        return cijfer;
    }
}
