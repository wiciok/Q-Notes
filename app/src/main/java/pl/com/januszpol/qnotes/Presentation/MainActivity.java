package pl.com.januszpol.qnotes.Presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import pl.com.januszpol.qnotes.Model.Services.ColorService;
import pl.com.januszpol.qnotes.Presentation.NoteCreate.CreateNoteFragment;
import pl.com.januszpol.qnotes.Presentation.NoteEdit.EditNoteFragment;
import pl.com.januszpol.qnotes.Presentation.NotesList.NotesListFragment;
import pl.com.januszpol.qnotes.Presentation.noteshow.ShowNoteFragment;
import pl.com.januszpol.qnotes.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NotesListFragment notesListFragment;
    CreateNoteFragment createNoteFragment;
    FloatingActionButton fab;

    private ColorService colorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateNote();
                fab.hide();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.fragmentContainer) != null)
        {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    Object value = bundle.get(key);
                    if (value != null)
                    Log.d("MAIN", String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                }
            long noteId = intent.getLongExtra("note_id", -1);
            if (savedInstanceState != null)
            {
                return;
            }


            notesListFragment = new NotesListFragment();
            notesListFragment.setArguments(getIntent().getExtras());
            createNoteFragment = new CreateNoteFragment();
            createNoteFragment.setArguments(getIntent().getExtras());

            Log.d("mainact: ", "noteId2: " + noteId);
            if (-1 != noteId)
                goToNote(noteId);

            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, notesListFragment,"NOTES_LIST_FRAGMENT").commit();
        }


        colorService = new ColorService(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        colorService.RestoreColor();
    }

    private void goToNote(long noteId) {
        ShowNoteFragment showNoteFragment = new ShowNoteFragment();
        Bundle args = new Bundle();
        args.putLong("noteId", noteId);
        showNoteFragment.setArguments(args);
        ((FloatingActionButton)findViewById(R.id.fab)).hide();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, notesListFragment,"NOTES_LIST_FRAGMENT");
        transaction.replace(R.id.fragmentContainer, showNoteFragment);
        transaction.commit();
    }

    void goToList()
    {
        if(notesListFragment==null)
            notesListFragment=new NotesListFragment();
        fab.show();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, notesListFragment, "NOTES_LIST_FRAGMENT");
        transaction.commit();
    }

    void goToCreateNote()
    {
        if(createNoteFragment==null)
            createNoteFragment = new CreateNoteFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, createNoteFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.d("mainBack","cnt: " + getSupportFragmentManager().getBackStackEntryCount());
            NotesListFragment fragment = (NotesListFragment) getSupportFragmentManager().findFragmentByTag("NOTES_LIST_FRAGMENT");
            if (fragment != null && fragment.isVisible()) {
                super.onBackPressed();
            } else {
                goToList();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            runSettingsActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void runSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

            Toast.makeText(getApplicationContext(),"Poprawnie dodano nowego studenta",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
