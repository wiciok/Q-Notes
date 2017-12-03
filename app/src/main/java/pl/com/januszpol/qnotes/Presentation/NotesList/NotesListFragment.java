package pl.com.januszpol.qnotes.Presentation.NotesList;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import java.util.List;

import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.Model.Services.INoteService;
import pl.com.januszpol.qnotes.Model.Services.NoteService;
import pl.com.januszpol.qnotes.R;


public class NotesListFragment extends Fragment
{
    private INoteService noteService;
    private List<Note> notes;

    private ListView listView;
    private NoteAdapter noteAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteService = new NoteService();
        // TODO - do usunięcia
        createStartNotes();
        notes = noteService.getAllNotes();
        Log.d("NotesListFrg", "size: " + notes.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        listView = (ListView) view.findViewById(R.id.notesListView);
        noteAdapter = new NoteAdapter(getActivity(), notes);
        listView.setAdapter(noteAdapter);

        return view;
    }

    private void createStartNotes() {
        removeOldNotes();
        addNewNotes();
    }

    private void removeOldNotes() {
        List<Note> oldNotes = noteService.getAllNotes();
        for (Note note : oldNotes)
            noteService.removeNote(note);
    }

    private void addNewNotes() {
        for (int i = 1; i <= 10; i++) {
            Note note = new Note("Topic " + i, "DescriptionDescription DescriptionDescription " + i);
            noteService.addNote(note);
        }
    }
}
