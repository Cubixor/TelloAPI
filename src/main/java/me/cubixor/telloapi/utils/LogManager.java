package me.cubixor.telloapi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogManager {

    public static final String folderPath = System.getProperty("user.dir") + File.separator + "droneLog" + File.separator;

    public static String logFile = null;

    private static FileOutputStream logFileOutputStream;

    public void createFile() {
        File file = new File(folderPath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        logFile = folderPath + System.currentTimeMillis() + ".DAT";
    }

    public void writeToFile(byte[] data) {
        if (logFile == null) {
            createFile();
        }

        if (data != null && data.length > 0) {
            if (logFile == null) {
                createFile();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(logFile, true);
                logFileOutputStream = fileOutputStream;
                fileOutputStream.write(data);
                logFileOutputStream.flush();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                try {
                    logFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}