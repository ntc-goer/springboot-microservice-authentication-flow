package ntcgoer.sharingmodule.util;

import java.util.UUID;

public class StringUtil {
    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    public String generateRandomString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
