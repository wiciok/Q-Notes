package pl.com.januszpol.qnotes.Presentation.NoteEdit;

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

public class EditNoteFragment extends Fragment {
    private Button EditNoteButton;
    private Button EditNotification;
    private INoteService noteService;
    private EditText topic;
    private EditText description;
    private ListView dataListView;
    private List<Date> list;
    private DateListAdapter listAdapter;
    private View view;
    private Calendar date;
    private Note editNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_edit_note, container, false);
        noteService = new NoteService();
        Bundle args = getArguments();
        long noteId = args.getLong("noteId");
        editNote= noteService.getNoteById(noteId);
        topic=view.findViewById(R.id.topic_edit);
        description=view.findViewById(R.id.description_edit);

        if(editNote!=null) {
           topic.setText(editNote.getTopic());
           description.setText(editNote.getDescription());
            dataListView = view.findViewById(R.id.data_list_edit);
            list=new ArrayList<Date>();
            for(Notification x: editNote.getNotificationsList())
            {
                list.add(x.getExecuteDate());
            }
            listAdapter = new DateListAdapter(getActivity(), list);
            dataListView.setAdapter(listAdapter);
        }

        EditNotification=view.findViewById(R.id.edit_notification);

        EditNotification.setOnClickListener(new View.OnClickListener()
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

        EditNoteButton= view.findViewById(R.id.edit_note);
        EditNoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editNote.setTopic(topic.getText().toString());
                editNote.setDescription(description.getText().toString());

               /* Aktualizacja powiadomień
                List<Notification> listNoteNotification=editNote.getNotificationsList();

                listNoteNotification.clear();
                for(Date x:list)
                {
                    Notification newNotification=new Notification();
                    newNotification.setExecuteDate(x);
                    listNoteNotification.add(newNotification);
                }
                 */

                noteService.updateNote(editNote);

                NotesListFragment notesListFragment = new NotesListFragment();
                notesListFragment.setArguments(getActivity().getIntent().getExtras());

                Toast.makeText(getActivity(),"Notatka została edytowana",Toast.LENGTH_SHORT).show();
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
