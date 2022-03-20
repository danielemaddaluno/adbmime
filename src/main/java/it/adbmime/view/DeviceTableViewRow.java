package it.adbmime.view;

import it.adbmime.adb.devices.Device;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class DeviceTableViewRow {
    private final Device device;
    private final StringProperty idProp;
    private final StringProperty statusProp;

    private DeviceTableViewRow(Device device) {
        this.device = device;
        this.idProp = new SimpleStringProperty(device.getId());
        this.statusProp = new SimpleStringProperty(device.getStatus());
    }

    public static DeviceTableViewRow getInstance(Device device) {
        return new DeviceTableViewRow(device);
    }

    public Device getDevice() {
        return device;
    }

    public StringProperty getIdProp() {
        return idProp;
    }

    public StringProperty getStatusProp() {
        return statusProp;
    }
}
