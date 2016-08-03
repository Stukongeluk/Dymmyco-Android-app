package dymmyco.noobs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private Menu menu;
    private CourseListAdapter adapter;
    private List<CourseModel> courseModels = new ArrayList<>();
    private String username;
    private int requestCode = 1;
//    private int requestCode2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.tweedescherm);

        //Toast the welcome message
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();

        //Create the plus button and add some action to it.
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InvoerActivity.class);
                startActivity(new Intent(i));
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
        courseModels.add(new CourseModel("IKPMD", "3", "10", "2", ""));
        courseModels.add(new CourseModel("IKUE", "3", "10", "2", ""));

        adapter = new CourseListAdapter(SchermTweeActivity.this, 0, courseModels);
        courseList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

//       this.username = getName(ProfielActivity.getFile());

        this.username = MainActivity.username;

        MenuItem name = menu.findItem(R.id.action_header);
        name.setTitle(username);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                username = data.getStringExtra("username");
                updateMenuItem();
            }
//        } else if(requestCode == 2) {
//            if(resultCode == Activity.RESULT_OK) {
//
//            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
    }//onActivityResult

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_header:
                //The profile page
                startActivityForResult(new Intent(this, ProfielEditActivity.class), requestCode);
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

    public void updateMenuItem() {
        MenuItem menuName = menu.findItem(R.id.action_header);
        menuName.setTitle(username);
    }
}
