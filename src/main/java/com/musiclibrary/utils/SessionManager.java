package com.musiclibrary.utils;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    // Simulated session store
    private static final Map<String, Boolean> sessionStore = new HashMap<>();

    /**
     * Method to simulate starting a session for a given emailId.
     * If the session starts successfully, the emailId is added to the session
     * store with a value of true.
     * 
     * @param emailId the emailId for which to start the session
     * @return true if the session starts successfully, false otherwise
     */
    public static boolean startSession(String emailId) {
        if (emailId == null || emailId.trim().isEmpty()) {
            return false; // Session cannot start without a valid emailId
        }
        sessionStore.put(emailId, true); // Simulate starting the session by adding the emailId to the session store
        return true; // Indicate that the session started successfully
    }
    
    /**
     * Method to simulate ending a session for a given emailId.
     * If the session exists for the provided emailId, it is removed from the session
     * store, indicating the session has ended successfully.
     * 
     * @param emailId the emailId for which to end the session
     * @return true if the session was found and ended successfully, false otherwise
     */
    public static boolean endSession(String emailId) {
        if (sessionStore.containsKey(emailId)) {
            sessionStore.remove(emailId); // Remove the user's session
            return true; // Session ended successfully
        }
        return false; // Session could not be found/ended
    }
}
