package dymmyco.noobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

/**
 * Created by Dylan on 18-7-2016.
 */
public class SchermTweeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweedescherm);
        String naam = getIntent().getStringExtra("Naam");

        TextView name = (TextView) findViewById(R.id.textView);
        name.setText(naam);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
