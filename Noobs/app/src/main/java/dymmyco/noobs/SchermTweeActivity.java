package dymmyco.noobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import dymmyco.noobs.adapters.CourseListAdapter;
import dymmyco.noobs.models.CourseModel;

/**
 * This class handles the logic of the second activity screen
 * <p/>
 * Created by Dylan on 18-7-2016.
 * Edited by Jimmy
 */
public class SchermTweeActivity extends AppCompatActivity {
    private ListView courseList;
    private CourseListAdapter adapter;
    private List<CourseModel> courseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweedescherm);

        //Toast the welcome message
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();

        //Create the plus button and add some action to it.
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Placeholder bruuuuh", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
        courseList = (ListView) findViewById(R.id.my_list_view);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                  Toast t = Toast.makeText(SchermTweeActivity.this, "Positie kan die opvragen omg: " + position, Toast.LENGTH_SHORT);
                                                  t.show();
                                              }
                                          }
        );
        //hardcoded data jwz
        courseModels.add(new CourseModel("IKPMD", "3", "10", "2"));
        courseModels.add(new CourseModel("IKUE", "3", "10", "2"));

        adapter = new CourseListAdapter(SchermTweeActivity.this, 0, courseModels);
        courseList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        String userName = getName(ProfielActivity.getFile());

        MenuItem name = menu.findItem(R.id.action_header);
        name.setTitle(userName);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_header:
                //The profile page
                getApplicationContext().startActivity(new Intent(getApplicationContext(), ProfielEditActivity.class));
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
