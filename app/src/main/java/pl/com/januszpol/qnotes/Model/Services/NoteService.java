package pl.com.januszpol.qnotes.Model.Services;

import java.util.List;

import io.realm.Realm;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;

public class NoteService {
    private Realm realmInstance;

    public NoteService(){
        realmInstance=Realm.getDefaultInstance();
    }


    public List<Note> getAllNotes(){
        return realmInstance.where(Note.class).findAll();
    }

    public void addNote(Note newNote){
        realmInstance.beginTransaction();
        realmInstance.copyToRealm(newNote);
        realmInstance.commitTransaction();
    }
}
