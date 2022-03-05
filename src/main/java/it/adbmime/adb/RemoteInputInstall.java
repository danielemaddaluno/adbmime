package it.adbmime.adb;

import java.io.File;

/**
 *
 */
public final class RemoteInputInstall implements RemoteInput {
    private File apk;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputInstall(File apk){
        this.apk = apk;
    }

    static RemoteInputInstall newInstance(File apk) {
        return new RemoteInputInstall(apk);
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
