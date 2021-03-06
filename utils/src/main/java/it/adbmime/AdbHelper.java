package it.adbmime;

import it.adbmime.output.AdbStreamResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class AdbHelper {
    private static String EMPTY = "";
    private static String NEWLINE = "\n";

    private AdbHelper() {}

    /**
     *
     * @param command
     * @param timeout
     * <ul>
     *     <li>null --> waitFor</li>
     *     <li>>0 --> timeout</li>
     *     <li><=0 --> does not wait</li>
     * </ul>
     * @return
     */
    public static String run(String command, Integer timeout){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            if(timeout != null){
                if(timeout > 0){
                    pr.waitFor(timeout, TimeUnit.MILLISECONDS);
                }
            } else {
                pr.waitFor();
            }
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line=buf.readLine()) != null) {
                sb.append(line + NEWLINE);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return EMPTY;
        }
    }

    public static String run(String command){
        return run(command, null);
    }

    public static List<String> runForLines(String command){
        return Arrays.stream(run(command).split(NEWLINE)).collect(Collectors.toList());
    }

    public static byte[] runForImage(String command){
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
//            pr.waitFor(100, TimeUnit.MILLISECONDS);
            InputStream is = pr.getInputStream();
            return is.readAllBytes();
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
                String line;
                while ((line=buf.readLine()) != null) {
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
