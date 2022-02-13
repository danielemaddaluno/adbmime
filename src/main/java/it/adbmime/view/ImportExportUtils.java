package it.adbmime.view;

import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class ImportExportUtils {
    private ImportExportUtils(){}

    public static final String FILE_PREFIX = "Economic_Calendar_";
    public static final String ICS_EXTENSION = ".ics";

    public static void export(ObservableList<RemoteInputTableViewRow> remoteInputsData, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(RemoteInputTableViewRow row: remoteInputsData){
                writer.write(row.getRemoteInput().command());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private final static String DATE_FORMAT = "yyyy_MM_dd";
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String getFormattedDate(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

    public static String getFormattedNow() {
        return ZonedDateTime.now().format(TIME_FORMATTER);
    }

    public static String getDate() {
        return getFormattedDate(new Date());
    }
}
