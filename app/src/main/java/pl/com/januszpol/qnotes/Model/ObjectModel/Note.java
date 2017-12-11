package pl.com.januszpol.qnotes.Model.ObjectModel;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {
    @PrimaryKey
    private long id;

    private String topic;
    private String description;
    private Date creationDate;
    private Date lastEditDate;
    private RealmList<Attachment> attachmentsList;          //should be Attachable, but RealmList doesn't support polymorphism
    private RealmList<NoteNotification> notificationsList;

    public static String id_field = "id";

    public Note() {
        this.creationDate=new Date();
        this.lastEditDate=new Date();
        this.attachmentsList = new RealmList<>();
        this.notificationsList = new RealmList<>();
    }

    public Note(String topic){
        this();
        this.topic=topic;
    }

    public Note(String topic, String description){
        this(topic);
        this.description=description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public List<Attachment> getAttachmentsList() {
        return attachmentsList;
    }

    public List<NoteNotification> getNotificationsList() {
        return notificationsList;
    }


    public void updateEditDate(){
        lastEditDate=new Date();
    }
}
