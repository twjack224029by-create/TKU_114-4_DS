import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class LoginLog {
    private String username;
    private String ipAddress;
    private String timestamp;

    public LoginLog(String username, String ipAddress, String timestamp) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    public String getUsername() { return username; }
    public String getIpAddress() { return ipAddress; }
    public String getTimestamp() { return timestamp; }
}

class AccountStats {
    private String username;
    private int totalLogins;             
    private Set<String> uniqueIps;       

    public AccountStats(String username) {
        this.username = username;
        this.totalLogins = 0;
        this.uniqueIps = new HashSet<>();
    }

    public void addLogin(String ip) {
        this.totalLogins++;
        this.uniqueIps.add(ip);
    }

    public String getUsername() { return username; }
    public int getTotalLogins() { return totalLogins; }
    public Set<String> getUniqueIps() { return uniqueIps; }
    public int getUniqueIpCount() { return uniqueIps.size(); }
}
