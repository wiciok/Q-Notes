package pl.com.januszpol.qnotes.Presentation.NotesList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.R;

/**
 * Created by Kordian on 03.12.2017.
 */

public class DateListAdapter extends ArrayAdapter<Date> {

    private Context context;
    private List<Date> data = new ArrayList<Date>();

    public DateListAdapter(Context context, List<Date> dataItem) {
        super(context, R.layout.date_item, dataItem);
        this.data = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.date_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Date temp = getItem(position);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(temp);
        viewHolder.title.setText(today);
        return convertView;
    }

    public List<Date> getData() {
        return data;
    }

    public void setData(List<Date> data) {
        this.data = data;
    }

    public class ViewHolder {
        TextView title;
    }
}
