import gui.LoginFrame;
import sgcr.SGCR;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Main {
    public static final Logger LOGGER = Logger.getLogger("CR");

    public static void main(String[] args) {
        loadLoggerSettings();

        SGCR sgcr = new SGCR();
        sgcr.registarTrabalhador("fn14", "1234", "1234");

        LoginFrame loginFrame = new LoginFrame(sgcr, 800, 600);
        Thread thread = new Thread(loginFrame);
        thread.start();
    }

    private static void loadLoggerSettings(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            SimpleFormatter formatter = new SimpleFormatter(){
                @Override
                public String format(LogRecord record) {
                    StringBuilder sb = new StringBuilder();
                    sb.append('[').append(record.getLoggerName()).append("] - ");
                    sb.append(dateFormat.format(new Date(record.getMillis()))).append(" - ");
                    sb.append(record.getLevel().getLocalizedName()).append(" - ");
                    sb.append(record.getMessage()).append('\n');
                    if(record.getThrown() != null) sb.append(record.getThrown()).append('\n');
                    return sb.toString();
                }
            };
            FileHandler fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(formatter);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            LOGGER.warning("Failed to open log file. Logs won't be saved.");
        }
    }
}
