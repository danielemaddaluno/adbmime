package it.adbmime.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class AdbHelper {

    private static String run(String command){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line=buf.readLine())!=null) {
                sb.append(line);
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

}
