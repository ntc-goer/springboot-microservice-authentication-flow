package ntcgoer.sharingmodule.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashUtil {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String encode(String str) {
        return passwordEncoder.encode(str);
    }
    public Boolean matches(String pureStr, String hashedStr) {
        return passwordEncoder.matches(pureStr, hashedStr);
    }
}
