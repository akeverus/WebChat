package client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Log {
    private static PrintWriter out;

    private static String getLogFileByLogin(String login) {
        return "log/log_" + login + ".txt";
    }

    public static void startLog(String login) {
        try {
            out = new PrintWriter(new FileOutputStream(getLogFileByLogin(login), true), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void stopLog() {
        if (out != null) {
            out.close();
        }
    }

    public static void writeLine(String msg) {
        out.println(msg);
    }

    public static String getLastHundredMsgFromLog(String login) {
        if (!Files.exists(Paths.get(getLogFileByLogin(login)))) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            List<String> logLines = Files.readAllLines(Paths.get(getLogFileByLogin(login)));
            int startPosition = 0;
            if (logLines.size() > 100) {
                startPosition = logLines.size() - 100;
            }
            for (int i = startPosition; i < logLines.size(); i++) {
                sb.append(logLines.get(i)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}