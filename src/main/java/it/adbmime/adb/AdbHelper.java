package it.adbmime.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public abstract class AdbHelper {

    protected static <T extends AdbStreamResult> T getAdbStreamResult(Class<T> clazz){
        try {
            AdbStreamResult result = clazz.newInstance();
            while(true) {
                Runtime run = Runtime.getRuntime();
                Process pr = run.exec("adb shell getevent -l | grep ABS_MT_POSITION");
                pr.waitFor(1, TimeUnit.SECONDS);
                BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line = "";
                while ((line=buf.readLine())!=null) {
                    if(result.isReady(line)){
                        return (T) result;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public static PhysicalSize getSize(){
        String cmd = run("adb shell wm size");
        return new PhysicalSize(cmd);
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
