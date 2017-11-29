package pl.com.januszpol.qnotes.Model.ObjectModel;

import io.realm.RealmObject;
import pl.com.januszpol.qnotes.Model.ObjectModel.Interfaces.Attachable;
import pl.com.januszpol.qnotes.Model.ObjectModel.Interfaces.AttachmentType;

/**
 * Created by wk on 29.11.2017.
 */

public class Attachment extends RealmObject implements Attachable {
    private String name;
    private String attachmentType;

    public Attachment(){

    }

    public Attachment(String name, AttachmentType attachmentType) {
        this();
        this.name = name;
        setAttachmentType(attachmentType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.valueOf(attachmentType);
    }

    private void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType.toString();
    }
}
