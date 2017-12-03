package pl.com.januszpol.qnotes;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Kordian on 03.12.2017.
 */

public class QnotesApplication extends Application {

    @Override
    public void onCreate() {
        Realm.init(getApplicationContext());
        super.onCreate();
    }
}
