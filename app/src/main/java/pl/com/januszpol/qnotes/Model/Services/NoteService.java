package pl.com.januszpol.qnotes.Model.Services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.notifications.NotificationManager;

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
        List<Note> notes = realmInstance.where(Note.class).findAll();
        return realmInstance.copyFromRealm(notes);
    }

    public Note getNoteById(long id){
        Note note = realmInstance.where(Note.class).equalTo("id", id).findFirst();
        return realmInstance.copyFromRealm(note);
    }

    public void addNote(Note newNote){
        newNote.setId(getNextId());
        realmInstance.beginTransaction();
        realmInstance.copyToRealm(newNote);
        realmInstance.commitTransaction();
        NotificationManager.scheduleNoteNotifications(newNote);
    }

    public void updateNote(Note note){
        Note oldNote = getNoteById(note.getId());
        NotificationManager.removeNoteNotifications(oldNote);
        realmInstance.beginTransaction();
        realmInstance.copyToRealmOrUpdate(note);
        realmInstance.commitTransaction();
        NotificationManager.scheduleNoteNotifications(note);
    }

    public void removeNote(Note note) {
        NotificationManager.removeNoteNotifications(note);
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
