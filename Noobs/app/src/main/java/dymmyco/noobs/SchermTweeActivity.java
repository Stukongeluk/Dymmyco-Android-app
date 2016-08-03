package dymmyco.noobs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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

    private View header;


    private PieChart pieChart;
    private float[] yData = { 30, 20 };
    private String[] xData = {"Behaalde EC's", "EC's over"};

    private ProgressBar progressBar;

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

        //Add chart to view
        addChart();
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

    public void addChartData() {
        ArrayList<String> xVals = new ArrayList<>();
        //Add the hardcoded string values
        for (String names: xData) {
            xVals.add(names);
        }

        ArrayList<PieEntry> dataValues = new ArrayList<>();
        int i = 0;
        //Add the hardcoded value data
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
        pieChart.setDescription("Aantal EC's behaald in vergelijking met aantal te halen EC's");
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
}
