package it.adbmime.adb.input;

import it.adbmime.adb.AdbHelper;

import java.io.File;

/**
 *
 */
public final class RemoteInputAppInstall implements RemoteInput {
    private File apk;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputAppInstall(File apk){
        this.apk = apk;
    }

    static RemoteInputAppInstall newInstance(File apk) {
        return new RemoteInputAppInstall(apk);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.INSTALL;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), apk.getAbsolutePath());
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputInstall{" +
                "apk=" + apk != null ? apk.getAbsolutePath() : "" +
                '}';
    }
}
