package pl.com.januszpol.qnotes.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import io.realm.Realm;
import pl.com.januszpol.qnotes.Model.ObjectModel.Note;
import pl.com.januszpol.qnotes.Model.Services.NoteService;
import pl.com.januszpol.qnotes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(getApplicationContext());

        NoteService test = new NoteService();
        test.addNote(new Note("testTopic", "testDesc"));
        List<Note> tttt = test.getAllNotes();
        Note testNote = tttt.get(0);
    }
}
