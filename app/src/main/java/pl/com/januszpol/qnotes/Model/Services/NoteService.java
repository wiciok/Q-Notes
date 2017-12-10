package pl.com.januszpol.qnotes.Model.Services;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;

public class NoteService implements INoteService {
    private Realm realmInstance;

    public NoteService(){
         /*RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .encryptionKey(getKey())
                .schemaVersion(42)
                .modules(new MySchemaModule())
                .migration(new MyMigration())
                .build();*/
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("qnotesRealm")
                .schemaVersion(1)
                .build();

        realmInstance=Realm.getInstance(config);
    }


    public List<Note> getAllNotes(){
        return realmInstance.where(Note.class).findAll();
    }

    public Note getNoteById(long id){
        return realmInstance.where(Note.class).equalTo("id", id).findFirst();
    }

    public void addNote(Note newNote){
        newNote.setId(getNextId());
        realmInstance.beginTransaction();
        realmInstance.copyToRealm(newNote);
        realmInstance.commitTransaction();
    }

    public void updateNote(Note note){
        realmInstance.beginTransaction();
        realmInstance.copyToRealmOrUpdate(note);
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
