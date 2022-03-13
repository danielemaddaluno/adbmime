package it.adbmime.adb.devices;

public class Device {
    private String id;
    private String status;
    private String name;
    private String ip;

    private Device(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public static Device newInstance(String id, String status){
        return new Device(id, status);
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }
}
