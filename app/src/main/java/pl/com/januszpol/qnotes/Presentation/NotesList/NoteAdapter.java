package pl.com.januszpol.qnotes.Presentation.NotesList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.R;

/**
 * Created by Kordian on 03.12.2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private Context context;
    private List<Note> data = new ArrayList<Note>();

    public NoteAdapter(Context context, List<Note> dataItem) {
        super(context, R.layout.note_list_item, dataItem);
        this.data = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.note_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView
                    .findViewById(R.id.noteListTextViewTitle);
            viewHolder.description = (TextView) convertView
                    .findViewById(R.id.noteListTextViewDesc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Note temp = getItem(position);
        viewHolder.title.setText(temp.getTopic());
        viewHolder.description.setText(temp.getDescription());
        return convertView;
    }

    public List<Note> getData() {
        return data;
    }

    public void setData(List<Note> data) {
        this.data = data;
    }

    public class ViewHolder {
        TextView title;
        TextView description;
    }
}
