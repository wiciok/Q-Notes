package pl.com.januszpol.qnotes.Presentation.NotesList;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.Model.Services.FontService;
import pl.com.januszpol.qnotes.Model.Services.INoteService;
import pl.com.januszpol.qnotes.Model.Services.NoteService;
import pl.com.januszpol.qnotes.Presentation.MainActivity;
import pl.com.januszpol.qnotes.Presentation.NoteEdit.EditNoteFragment;
import pl.com.januszpol.qnotes.Presentation.noteshow.ShowNoteFragment;
import pl.com.januszpol.qnotes.R;
import pl.com.januszpol.qnotes.notifications.NotificationPublisher;


public class NotesListFragment extends Fragment
{
    private INoteService noteService;
    private List<Note> notes;

    private ListView listView;
    private NoteAdapter noteAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteService = new NoteService();
        notes = noteService.getAllNotes();
        Log.d("NotesListFrg", "size: " + notes.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        listView = (ListView) view.findViewById(R.id.notesListView);
        setListOnClickListener();
        noteAdapter = new NoteAdapter(getActivity(), notes);

        FontService.initFont(getActivity());

        listView.setAdapter(noteAdapter);
        registerForContextMenu(listView);
        Log.d("onCreateView", "");
        return view;
    }

    private void setListOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long noteId = noteAdapter.getData().get(position).getId();
                goToShowNoteActivity(noteId);
            }
        });
    }

    private void goToShowNoteActivity(long noteId) {
        ShowNoteFragment showNoteFragment = new ShowNoteFragment();
        Bundle args = new Bundle();
        args.putLong("noteId", noteId);
        showNoteFragment.setArguments(args);
        ((FloatingActionButton)getActivity().findViewById(R.id.fab)).hide();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, showNoteFragment);
        transaction.commit();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.notesListView)
        {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_notes_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final long noteId = noteAdapter.getItemId(info.position);
        final Note note = noteAdapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.edit:
                EditNoteFragment editNoteFragment = new EditNoteFragment();
                Bundle args = new Bundle();
                args.putLong("noteId", noteId);
                editNoteFragment.setArguments(args);
                ((FloatingActionButton)getActivity().findViewById(R.id.fab)).hide();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, editNoteFragment);
                transaction.commit();
                return true;
            case R.id.delete:
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Usuwanie notatki")
                        .setMessage("Czy na pewno chcesz usunąć notatkę: "+note.getTopic()+" ?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                noteService.removeNote(note);
                                updateNotesList();
                                Toast.makeText(getActivity(),"Notatka została usunięta",Toast.LENGTH_SHORT).show();
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
            default:
                return super.onContextItemSelected(item);
        }
    }

    void updateNotesList()
    {
        notes = noteService.getAllNotes();
        listView = (ListView) view.findViewById(R.id.notesListView);
        noteAdapter = new NoteAdapter(getActivity(), notes);
        listView.setAdapter(noteAdapter);
    }
}
