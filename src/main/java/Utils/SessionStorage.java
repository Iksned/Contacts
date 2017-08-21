package Utils;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {
    private static Map<String,String> sessions = new HashMap<>();

    public static Map<String, String> getSessions() {
        return sessions;
    }

    public static void setSessions(Map<String, String> sessions) {
        SessionStorage.sessions = sessions;
    }
}
