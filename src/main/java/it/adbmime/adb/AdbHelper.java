package it.adbmime.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public abstract class AdbHelper {

    protected static String run(String command){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line=buf.readLine())!=null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    protected static String run(String command, int timeout, int rows){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            pr.waitFor(timeout, TimeUnit.SECONDS);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            StringBuffer sb = new StringBuffer();
            int size = 0;
            while ((line=buf.readLine())!=null && size < rows) {
                sb.append(line + "\n");
                size++;
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static PhysicalSize getSize(){
        String cmd = run("adb shell wm size");
        return new PhysicalSize(cmd);
    }

    public static PhysicalTouch getTouch(){
        String cmd = AdbHelper.run("adb shell getevent -l | grep ABS_MT_POSITION", 4, 4);
        return new PhysicalTouch(cmd);
    }

    // adb shell getevent -l | grep ABS_MT_POSITION

    /*
    adb shell input tap 1919 1079
    adb connect 192.168.43.1:5555
    adb disconnect
    adb shell ifconfig
    adb shell input tap x y
     */

}
