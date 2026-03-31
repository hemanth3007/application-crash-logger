
import java.util.*;

public class CrashLogService {

    private List<CrashLog> logs;

    //Constructor - Called when creating a new CrashLogService Object to create an empty arraylist of CrashLogs
    public CrashLogService() {
        logs = new ArrayList<>();
    }

    //Method to add a CrashLog to the arraylist of logs
    public boolean addCrashLog(String crashId, String message) {
        try {
            if (crashId == null || crashId.isEmpty() || message == null || message.isEmpty()) {
                throw new IllegalArgumentException("CrashLog cannot be null or empty");
            }

            String crashType = extractCrashType(message);
            CrashLog log = new CrashLog(crashId, crashType, message);
            logs.add(log);
            return true;
        } catch (Exception e) {
            System.out.println("Error adding log: " + e.getMessage());
            return false;
        }
    }

    //Method to extract crash type from the message.
    public String extractCrashType(String message) {
        try {
            int index = message.indexOf(":");
            if (index != -1) {
                return message.substring(0, index);
            } else {
                return "UNKNOWN";
            }
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    //Method to return the list of logs just to make info/warnings/error popups work in UI. Not used anywhere else.
    public List<CrashLog> getLogs() {
        return logs;
    }

    //Method to print all logs
    public String getAllLogs() {
        StringBuffer sb = new StringBuffer();
        try {
            if (logs.isEmpty()) {
                throw new NoSuchElementException("No crash logs available");
            }
            for (CrashLog log : logs) {
                sb.append(log.toString()).append("\n");
            }
        } catch (Exception e) {
            return "Info: " + e.getMessage();
        }
        return sb.toString();
    }

    //Filter unexpected crashes
    public String getUnexpectedCrashes() {
        StringBuffer sb = new StringBuffer();
        for (CrashLog log : logs) {
            if (log.getDetails().toLowerCase().contains("unexpected")) {
                sb.append(log.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    //Sort logs by crash id
    public void sortLogsById() {
        Collections.sort(logs, new CrashLogComparator());
    }
}
