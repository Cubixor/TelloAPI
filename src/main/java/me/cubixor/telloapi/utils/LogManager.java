package me.cubixor.telloapi.utils;

import java.io.File;
import java.io.FileOutputStream;

public class LogManager {

    public static final String folderPath = System.getProperty("user.dir") + File.separator + "droneLog" + File.separator;

    public static String logFile = null;

    private static FileOutputStream logFileOutputStream;

    private LogManager() {
    }

    public static LogManager getInstance() {
        return C1546a.instance;
    }

    public void createFile() {
        File file = new File(folderPath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        logFile = folderPath + System.currentTimeMillis() + ".DAT";
    }

    public void writeToFile(byte[] data) {
        if (data != null && data.length > 0) {
            if (logFile == null) {
                createFile();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(logFile, true);
                logFileOutputStream = fileOutputStream;
                fileOutputStream.write(data);
                logFileOutputStream.flush();
                try {
                    logFileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                //C1575t.m5171a(e2.getMessage());
                try {
                    logFileOutputStream.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    logFileOutputStream.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        }
    }

    private static class C1546a {
        public static LogManager instance = new LogManager();
    }
}