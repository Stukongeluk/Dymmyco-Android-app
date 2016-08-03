package dymmyco.noobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dymmyco.noobs.adapters.CourseListAdapter;
import dymmyco.noobs.models.CourseModel;

/**
 * Created by Dylan on 28-7-2016.
 */
public class InvoerActivity extends AppCompatActivity {
    private ListView courseList;
    private CourseListAdapter adapter;
    private List<CourseModel> courseModels = new ArrayList<>();

    public int modelPosition;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        setContentView(R.layout.invoerscherm);

        FloatingActionButton btnRefresh = (FloatingActionButton) findViewById(R.id.invoer_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        courseList = (ListView) findViewById(R.id.my_list_view);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                  Intent i = new Intent(getApplicationContext(), CijferPopupActivity.class);
                                                  i.putExtra("vak", courseModels.get(position));
                                                  startActivityForResult(i, 1);
                                                  modelPosition = position;
                                              }
                                          }
        );

        loadCijfers();
    }
    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                CourseModel temp = courseModels.get(modelPosition);
                temp.grade = data.getStringExtra("cijfer");
                adapter = new CourseListAdapter(InvoerActivity.this, 0, courseModels);
                courseList.setAdapter(adapter);
                saveCijfers();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem name = menu.findItem(R.id.action_header);
        name.setTitle(MainActivity.username);
        return true;
    }

    public void saveCijfers() {
        try {
            FileOutputStream fos = openFileOutput("VakInfo", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(courseModels);
            os.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadCijfers() {
        try {
            Log.d("File Found: ", "Using saved data");
            FileInputStream fis = openFileInput("VakInfo");
            ObjectInputStream ois = new ObjectInputStream(fis);
            courseModels = (List<CourseModel>) ois.readObject();
            ois.close();
            fis.close();
            CourseListAdapter adapterSavedCijfers = new CourseListAdapter(InvoerActivity.this, 0, courseModels);
            courseList.setAdapter(adapterSavedCijfers);
        } catch (FileNotFoundException ex) {
            Log.d("File Not Found: ", "Using pre-made template");
            //hardcoded data jwz
            courseModels.add(new CourseModel("IKPMD", "3", "0", "2", "Dit vak is IKPMD"));
            courseModels.add(new CourseModel("IRDBMS", "3", "0", "2", "Dit vak is IRDBMS"));
            courseModels.add(new CourseModel("ISCP", "3", "0", "2", "Dit vak is ISCP"));
            courseModels.add(new CourseModel("IQUA", "3", "0", "2", "Dit vak is IQUA"));
            courseModels.add(new CourseModel("IETH", "3", "0", "2", "Dit vak is IETH"));
            courseModels.add(new CourseModel("IPSEN2", "6", "0", "2", "Dit vak is IPSEN2"));
            courseModels.add(new CourseModel("IPSEN3", "6", "0", "2", "Dit vak is IPSEN3"));
            courseModels.add(new CourseModel("IPSEN4", "6", "0", "2", "Dit vak is IPSEN4"));
            courseModels.add(new CourseModel("IPSEN5", "6", "0", "2", "Dit vak is IPSEN5"));
            courseModels.add(new CourseModel("IOPR3", "3", "0", "2", "Dit vak is IOPR3"));
            courseModels.add(new CourseModel("IRDM", "3", "0", "2", "Dit vak is IRDM"));
            courseModels.add(new CourseModel("ILG1", "3", "0", "2", "Dit vak is ILG1"));
            courseModels.add(new CourseModel("IIAD", "3", "0", "2", "Dit vak is IIAD"));
            courseModels.add(new CourseModel("ICOMMH", "3", "0", "2", "Dit vak is ICOMMH"));
            courseModels.add(new CourseModel("ISLH", "3", "0", "2", "Dit vak is ISLH"));

            adapter = new CourseListAdapter(InvoerActivity.this, 0, courseModels);
            courseList.setAdapter(adapter);

            saveCijfers();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_header:
                startActivity(new Intent(this, ProfielActivity.class));
                return true;
            case R.id.action_help:
                loadCijfers();
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
