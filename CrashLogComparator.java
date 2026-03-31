
import java.util.Comparator;

public class CrashLogComparator implements Comparator<CrashLog> {

    @Override
    public int compare(CrashLog log1, CrashLog log2) {
        return log1.getCrashId().compareTo(log2.getCrashId());
    }
}
