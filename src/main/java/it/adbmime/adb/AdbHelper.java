package it.adbmime.adb;

import java.io.BufferedReader;
import java.io.File;
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

    protected static String run(String command, File dir){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command, null, dir);
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

    public static <T extends AdbStreamResult> T getAdbStreamResult(Class<T> clazz){
        try {
            AdbStreamResult result = clazz.getDeclaredConstructor().newInstance();
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

    public static PhysicalSize getSize(){
        String cmd = run("adb shell wm size");
        return new PhysicalSize(cmd);
    }

    public static PhysicalScreen getScreen(){
        File file = null;
        try {
            file = File.createTempFile("screen", ".png");
            // file = new File("screen.png");
            String path = file.getAbsolutePath();
            // run("adb exec-out screencap -p > " + path);
            run("adb exec-out screencap -p > " + file.getName(), file.getParentFile());
            return new PhysicalScreen(file);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(file != null && file.exists()) {
                file.delete();
            }
        }
        return null;
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
