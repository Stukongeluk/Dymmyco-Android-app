package dymmyco.noobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoerscherm);

        courseList = (ListView) findViewById(R.id.my_list_view);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getApplicationContext(), CijferPopupActivity.class);
                startActivity(new Intent(i));
                modelPosition = position;
            }
        }
        );
        FloatingActionButton btnRefresh = (FloatingActionButton) findViewById(R.id.invoer_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseModel temp = courseModels.get(modelPosition);
                temp.grade = CijferPopupActivity.getCijfer();
            }
        });

        //hardcoded data jwz
        courseModels.add(new CourseModel("IKPMD", "3", "0", "2"));
        courseModels.add(new CourseModel("IRDBMS", "3", "0", "2"));
        courseModels.add(new CourseModel("ISCP", "3", "0", "2"));
        courseModels.add(new CourseModel("IQUA", "3", "0", "2"));
        courseModels.add(new CourseModel("IETH", "3", "0", "2"));
        courseModels.add(new CourseModel("IPSEN2", "6", "0", "2"));
        courseModels.add(new CourseModel("IPSEN3", "6", "0", "2"));
        courseModels.add(new CourseModel("IPSEN4", "6", "0", "2"));
        courseModels.add(new CourseModel("IPSEN5", "6", "0", "2"));
        courseModels.add(new CourseModel("IOPR3", "3", "0", "2"));
        courseModels.add(new CourseModel("IRDM", "3", "0", "2"));
        courseModels.add(new CourseModel("ILG1", "3", "0", "2"));
        courseModels.add(new CourseModel("IIAD", "3", "0", "2"));
        courseModels.add(new CourseModel("ICOMMH", "3", "0", "2"));
        courseModels.add(new CourseModel("ISLH", "3", "0", "2"));

        adapter = new CourseListAdapter(InvoerActivity.this, 0, courseModels);
        courseList.setAdapter(adapter);

    }
}
