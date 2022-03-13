package it.adbmime.adb.output;

import it.adbmime.adb.AdbHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * https://gist.github.com/acrookston/6894900
 */
public final class DevicesList {
    private final static String TAB = "\t";
    protected static final String DEVICES_LIST = "adb devices";
    private final List<String> devices;

    private DevicesList(){
        List<String> lines = AdbHelper.runForLines(DEVICES_LIST);
        this.devices = lines
                .stream()
                .filter(l -> l.contains(TAB))
                .map(l -> l.split(TAB)[0])
                .collect(Collectors.toList());
    }

    protected static DevicesList newInstance() {
        return new DevicesList();
    }

    public List<String> getDevices() {
        return devices;
    }
}
