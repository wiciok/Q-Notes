package pl.com.januszpol.qnotes.Presentation.noteshow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.Model.ObjectModel.NoteNotification;
import pl.com.januszpol.qnotes.Model.Services.INoteService;
import pl.com.januszpol.qnotes.Model.Services.NoteService;
import pl.com.januszpol.qnotes.Presentation.NotesList.DateListAdapter;
import pl.com.januszpol.qnotes.Presentation.NotesList.NotesListFragment;
import pl.com.januszpol.qnotes.R;

public class ShowNoteFragment extends Fragment {
    private INoteService noteService;
    private TextView topic;
    private TextView description;
    private View view;
    private Note editNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_show_note, container, false);
        noteService = new NoteService();
        Bundle args = getArguments();
        long noteId = args.getLong("noteId");
        editNote= noteService.getNoteById(noteId);
        topic=view.findViewById(R.id.topic_show);
        description=view.findViewById(R.id.description_show);

        if(editNote!=null) {
           topic.setText(editNote.getTopic());
           description.setText(editNote.getDescription());
        }

        return view;
    }
}
