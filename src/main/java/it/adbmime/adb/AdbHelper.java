package it.adbmime.adb;

import it.adbmime.adb.output.AdbStreamResult;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

public abstract class AdbHelper {
    public static String run(String command){
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

    public static Image runForImage(String command){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
//            pr.waitFor(100, TimeUnit.MILLISECONDS);
            Image image = new Image(pr.getInputStream());
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends AdbStreamResult> T runForAdbStreamResult(String command, Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            AdbStreamResult result = constructor.newInstance();
            while(true) {
                Runtime run = Runtime.getRuntime();
                Process pr = run.exec(command);
                pr.waitFor(500, TimeUnit.MILLISECONDS);
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
}
