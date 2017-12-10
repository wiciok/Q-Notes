package pl.com.januszpol.qnotes.Model.Services;

import java.util.List;

import io.realm.RealmResults;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;

/**
 * Created by Kordian on 03.12.2017.
 */

public interface INoteService {

    public List<Note> getAllNotes();

    public void addNote(Note newNote);

    public void removeNote(Note note);

    public Note getNoteById(long id);

    public void updateNote(Note note);

}
