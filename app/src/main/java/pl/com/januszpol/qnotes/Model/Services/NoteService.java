package pl.com.januszpol.qnotes.Model.Services;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;

public class NoteService implements INoteService {
    private Realm realmInstance;

    public NoteService(){
        realmInstance=Realm.getDefaultInstance();
    }


    public List<Note> getAllNotes(){
        return realmInstance.where(Note.class).findAll();
    }

    public void addNote(Note newNote){
        newNote.setId(getNextId());
        realmInstance.beginTransaction();
        realmInstance.copyToRealm(newNote);
        realmInstance.commitTransaction();
    }

    public void removeNote(Note note) {
        realmInstance.beginTransaction();
        RealmResults<Note> foundNote = realmInstance.where(Note.class).equalTo(Note.id_field, note.getId()).findAll();
        foundNote.deleteAllFromRealm();
        realmInstance.commitTransaction();
    }

    private long getNextId() {
        long id;
        try {
            Number numb = realmInstance.where(Note.class).max(Note.id_field);
            if (null != numb)
                id = numb.longValue() + 1;
            else
                id = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            id = 1;
        }
        return id;
    }
}
