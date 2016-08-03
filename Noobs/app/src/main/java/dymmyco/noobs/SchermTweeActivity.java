package dymmyco.noobs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
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
    private LinearLayout courseList;
    private Menu menu;
    private CourseListAdapter adapter;
    private List<CourseModel> courseModels = new ArrayList<>();
    private List<CourseModel> filteredCourse = new ArrayList<>();
    private TextView adviesKopje;
    private TextView advies;
    private int red = R.color.redColor;
    private int orange = R.color.orangeColor;
    private int green = R.color.greenColor;

    private String username;
    private int requestCode = 1;

    private int adapterCount;

    private PieChart pieChart;
    private int maxEc;
    private int currentEc;
    private float[] yData = new float[2];
    private String[] xData = {"Behaalde EC's", "EC's restrerend"};

    private int whiteText = Color.rgb(248,248,255);


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
                startActivityForResult(i, 2);
            }
        });
        //Add the list below
        addList();
        //Add chart to view
        addChart();

        adviesKopje = (TextView) findViewById(R.id.adviesKopje);
        advies = (TextView) findViewById(R.id.adviesContext);
        generateAdvice(adviesKopje, advies);
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
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                courseList.invalidate();
                addList();
                addChart();
                generateAdvice(adviesKopje,advies);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
    }

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

    public void addChartData() {
        maxEc = 0;
        currentEc = 0;
        //Calculate the EC values for the chart
        for (CourseModel courses: courseModels) {
            maxEc += Integer.parseInt(courses.ects);
            if(Double.parseDouble(courses.grade) >= 5.5) {
                currentEc += Integer.parseInt(courses.ects);
            }
        }
        //Just setting 2 values, no loop needed
        yData[0] = currentEc;
        yData[1] = maxEc - currentEc;

        ArrayList<String> xVals = new ArrayList<>();
        //Add the hardcoded string values
        for (String names: xData) {
            xVals.add(names);
        }

        ArrayList<PieEntry> dataValues = new ArrayList<>();
        int i = 0;
        //Add thhe values
        for (float values: yData) {
            dataValues.add(new PieEntry(values, xVals.get(i)));
            i++;
        }
        //set Colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(0,128,128));
        colors.add(Color.rgb(205,0,0));

        //Create piechart dataset
        PieDataSet dataSet = new PieDataSet(dataValues, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(12f);

        PieData piechartData = new PieData(dataSet);
        piechartData.setValueTextColor(whiteText);
        piechartData.setValueTextSize(30f);

        pieChart.setData(piechartData);

        pieChart.highlightValue(null);

        pieChart.invalidate();
    }

    public void addChart() {
        //Instantiate piechart
        pieChart = (PieChart) findViewById(R.id.piechart);
        //configuring piechart
        pieChart.setUsePercentValues(false);
        pieChart.setDescription("");
        pieChart.setCenterText("Voortgang van amazing 2e jaar");

        // enable hole
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(30);
        pieChart.setHoleColor(R.color.colorBackground);
        pieChart.setTransparentCircleRadius(40);
        pieChart.setCenterTextColor(whiteText);

        addChartData();

        //set legend
        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
    }

    public void addList() {
        //Clearing stuff
        filteredCourse.clear();
        if(courseList != null) {
            courseList.removeAllViews();
        }
        //Create adapter
        adapter = new CourseListAdapter(SchermTweeActivity.this, 0, filteredCourse);
        courseList = (LinearLayout) findViewById(R.id.wannabeListView);

        courseModels = loadCijfers();

        //Filter the courses which the student hasn't passed yet
        for (CourseModel filteredModel: courseModels) {
            if((Double.parseDouble(filteredModel.grade)) < 5.5) {
                filteredCourse.add(filteredModel);
            }
        }

        adapterCount = adapter.getCount();
        //Loop through adapter and add the item to the view
        for(int i = 0; i< adapterCount; i++){
            View item = adapter.getView(i, null, null);
            courseList.addView(item);
        }
    }

    public ArrayList<CourseModel> loadCijfers() {
        try {
            Log.d("File Found: ", "Using saved data");
            FileInputStream fis = openFileInput("VakInfo");
            ObjectInputStream ois = new ObjectInputStream(fis);
            courseModels = (List<CourseModel>) ois.readObject();
            ois.close();
            fis.close();
            return (ArrayList<CourseModel>) courseModels;
        } catch (FileNotFoundException ex) {
            Log.d("File Not Found: ", "Using pre-made template");
            //If file doesn't exist, dynamically add all the models this way ;D
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

            return (ArrayList<CourseModel>) courseModels;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ArrayList<CourseModel>) courseModels;
    }

    public void generateAdvice(TextView title, TextView advice) {
        if(currentEc < 40) {
            title.setTextColor(ContextCompat.getColor(getApplicationContext(),red));
            advice.setText(R.string.main_advice_red);
        } else if( currentEc > 40 && currentEc < 53 ) {
            title.setTextColor(ContextCompat.getColor(getApplicationContext(),orange));
            advice.setText(R.string.main_advice_orange);
        } else {
            title.setTextColor(ContextCompat.getColor(getApplicationContext(),green));
            advice.setText(R.string.main_advice_green);
        }
    }
}
