package pl.com.januszpol.qnotes;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * Created by Kordian on 03.12.2017.
 */

public class QnotesApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        QnotesApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return QnotesApplication.context;
    }
}
