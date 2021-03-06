package pl.com.januszpol.qnotes.Presentation.NoteCreate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.Model.ObjectModel.Notification;
import pl.com.januszpol.qnotes.Model.Services.INoteService;
import pl.com.januszpol.qnotes.Model.Services.NoteService;
import pl.com.januszpol.qnotes.Presentation.MainActivity;
import pl.com.januszpol.qnotes.Presentation.NotesList.DateListAdapter;
import pl.com.januszpol.qnotes.Presentation.NotesList.NotesListFragment;
import pl.com.januszpol.qnotes.R;
public class CreateNoteFragment extends Fragment
{
    private Button createNoteButton;
    private Button createNotification;
    private INoteService noteService;
    private EditText topic;
    private EditText description;
    private ListView dataListView;
    private DatePickerDialog dialog;
    private List<Date>list;
    private DateListAdapter listAdapter;
    private View view;
    Calendar date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_create_note, container, false);
        noteService = new NoteService();
        dataListView = view.findViewById(R.id.data_list);
        list=new ArrayList<Date>();
        listAdapter = new DateListAdapter(getActivity(), list);
        dataListView.setAdapter(listAdapter);

        topic=view.findViewById(R.id.topic);
        description=view.findViewById(R.id.description);

        createNotification=view.findViewById(R.id.create_notification);

        createNotification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar currentDate = Calendar.getInstance();
                date = Calendar.getInstance();
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                Date myDate=date.getTime();
                                list.add(myDate);
                                listAdapter = new DateListAdapter(getContext(), list);
                                dataListView.setAdapter(listAdapter);
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
            }
        });

        createNoteButton= view.findViewById(R.id.create_note);
        createNoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Note newNote=new Note();
                newNote.setTopic(topic.getText().toString());
                newNote.setDescription(description.getText().toString());
                List<Notification> listNoteNotification=newNote.getNotificationsList();
                for(Date x:list)
                {
                    Notification newNotification=new Notification();
                    newNotification.setExecuteDate(x);
                    listNoteNotification.add(newNotification);
                }
                noteService.addNote(newNote);

                NotesListFragment notesListFragment = new NotesListFragment();
                notesListFragment.setArguments(getActivity().getIntent().getExtras());

                Toast.makeText(getActivity(),"Notatka została dodana",Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, notesListFragment);
                transaction.commit();
                ((FloatingActionButton)getActivity().findViewById(R.id.fab)).show();
            }
        });

        dataListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView<?> arg0, View arg1, final int pos, long id)
            {
                final Date date= (Date) arg0.getAdapter().getItem(pos);
                AlertDialog dialog = new AlertDialog.Builder(arg1.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Usuwanie powiadomienia")
                        .setMessage("Czy na pewno chcesz usunąć to powiadomienie?" )
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                list.remove(date);
                                listAdapter = new DateListAdapter(getContext(), list);
                                dataListView.setAdapter(listAdapter);
                                Toast.makeText(getContext(),"Usunięto powiadomienie",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
                return true;
            }
        });


        return view;
    }
}
