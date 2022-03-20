package it.adbmime.input;

import java.io.File;

/**
 *
 */
public final class RemoteInputAppInstall extends RemoteInput {
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
    public String toString() {
        return "RemoteInputInstall{" +
                "apk=" + apk != null ? apk.getAbsolutePath() : "" +
                '}';
    }
}
