package it.adbmime.adb;

import javafx.scene.image.Image;

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

    protected static Image runForImage(String command){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            pr.waitFor(100, TimeUnit.MILLISECONDS);
            Image image = new Image(pr.getInputStream());
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static <T extends AdbStreamResult> T runForAdbStreamResult(String command, Class<T> clazz){
        try {
            AdbStreamResult result = clazz.getDeclaredConstructor().newInstance();
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

    public static PhysicalSize getSize(){
        return PhysicalSize.newInstance();
    }

    public static PhysicalTouch getTouch(){
        return PhysicalTouch.newInstance();
    }

    public static Screenshot getScreen(){
        return Screenshot.newInstance();
    }

    public static void pressHomeButton() {
        Key.HOME.press();
    }

    public static void pressBrowserButton() {
        Key.EXPLORER.press();
    }

    public static void writeText(String text) {
        WriteText.newInstance(text).write();
    }
}
