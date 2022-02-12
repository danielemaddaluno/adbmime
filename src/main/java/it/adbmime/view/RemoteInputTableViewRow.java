package it.adbmime.view;

import it.adbmime.adb.RemoteInput;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class RemoteInputTableViewRow {
    private final RemoteInput remoteInput;
    private final StringProperty iconProp;
    private final StringProperty typeProp;
    private final StringProperty cmndProp;

    private RemoteInputTableViewRow(RemoteInput remoteInput) {
        this.remoteInput = remoteInput;
        this.iconProp = new SimpleStringProperty(remoteInput.getClass().getSimpleName());
        this.typeProp = new SimpleStringProperty(remoteInput.getClass().getSimpleName());
        this.cmndProp = new SimpleStringProperty(remoteInput.command());
    }

    public static RemoteInputTableViewRow getInstance(RemoteInput remoteInput) {
        return new RemoteInputTableViewRow(remoteInput);
    }

    public RemoteInput getRemoteInput() {
        return remoteInput;
    }

    public StringProperty getIconProp() {
        return iconProp;
    }

    public StringProperty getTypeProp() {
        return typeProp;
    }

    public StringProperty getCmndProp() {
        return cmndProp;
    }
}
