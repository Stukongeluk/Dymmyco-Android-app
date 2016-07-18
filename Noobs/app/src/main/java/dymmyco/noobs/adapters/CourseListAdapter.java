package dymmyco.noobs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import dymmyco.noobs.R;
import dymmyco.noobs.models.CourseModel;

/**
 * Hinthint: https://developer.android.com/reference/android/widget/ArrayAdapter.html
 * Populates the listViews with items
 * Created by Jimmy on 19-7-2016.
 */
public class CourseListAdapter extends ArrayAdapter<CourseModel> {

    public CourseListAdapter(Context context, int resource, List<CourseModel> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Here, position is the index in the list, the convertView is the view to be
        //recycled (or created), and parent is the ListView itself.
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.view_content_row, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.subject_name);
            holder.code = (TextView) convertView.findViewById(R.id.subject_code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CourseModel courseModel = getItem(position);
        holder.name.setText(courseModel.name);
        holder.code.setText(courseModel.ects);
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView code;

    }
}
