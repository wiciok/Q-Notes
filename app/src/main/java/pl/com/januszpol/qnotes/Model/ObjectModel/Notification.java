package pl.com.januszpol.qnotes.Model.ObjectModel;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by wk on 29.11.2017.
 */

public class Notification extends RealmObject {
    private Date executeDate;

    public Notification(){
        executeDate = new Date();
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }
}
