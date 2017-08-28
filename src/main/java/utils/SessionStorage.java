package utils;

import ConModel.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static Map<String,User> sessions = new ConcurrentHashMap<>();

    public static Map<String, User> getSessions() {
        return sessions;
    }

    public static void setSessions(Map<String, User> sessions) {
        SessionStorage.sessions = sessions;
    }
}
