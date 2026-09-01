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

public class LoginActivityReport {

    private Map<String, AccountStats> accountStatsMap; // Key: username, Value: AccountStats

    public LoginActivityReport() {
        this.accountStatsMap = new HashMap<>();
    }

    public void processLogs(List<LoginLog> logs) {
        for (LoginLog log : logs) {
            String user = log.getUsername();
            String ip = log.getIpAddress();

            accountStatsMap.putIfAbsent(user, new AccountStats(user));
            accountStatsMap.get(user).addLogin(ip);
        }
    }

    public void printGeneralReport() {
        System.out.println("                        使用者登入活動總覽報表");
        System.out.printf(" %-9s  %-8s  %-12s  %s%n", "帳號", "總登入次數", "獨立IP數", "使用過的IP列表");

        for (AccountStats stats : accountStatsMap.values()) {
            System.out.printf(" %-15s  %-12d  %-12d  %s%n",
                    stats.getUsername(),
                    stats.getTotalLogins(),
                    stats.getUniqueIpCount(),
                    stats.getUniqueIps());
        }
    }

    public void printAnomalyReport(int maxLoginThreshold, int maxIpThreshold) {
        System.out.printf("                 異常登入風險分析報告 (門檻: 登入 ≥ %d次 或 IP ≥ %d個)%n", 
                maxLoginThreshold, maxIpThreshold);

        List<String> suspiciousAccounts = new ArrayList<>();

        for (AccountStats stats : accountStatsMap.values()) {
            boolean isHighFrequency = stats.getTotalLogins() >= maxLoginThreshold;
            boolean isMultiIp = stats.getUniqueIpCount() >= maxIpThreshold;

            if (isHighFrequency || isMultiIp) {
                suspiciousAccounts.add(stats.getUsername());
                System.out.printf("風險帳號: %s%n", stats.getUsername());
                System.out.printf("   總登入次數: %d 次 %s%n", 
                        stats.getTotalLogins(), isHighFrequency ? "(高頻登入)" : "");
                System.out.printf("   獨立 IP 數量: %d 個 %s%n", 
                        stats.getUniqueIpCount(), isMultiIp ? "(異地/多IP登入)" : "");
                System.out.printf("   IP 紀錄: %s%n", stats.getUniqueIps());
                System.out.println("   建議處置: " + getSuggestedAction(isHighFrequency, isMultiIp));
            }
        }

        if (suspiciousAccounts.isEmpty()) {
            System.out.println("  檢測到符合風險條件的異常帳號。");
        } else {
            System.out.printf(" 統計摘要: 共發現 %d 個風險帳號，已列入安全追蹤清單。%n", suspiciousAccounts.size());
        }
    }

    private String getSuggestedAction(boolean highFreq, boolean multiIp) {
        if (highFreq && multiIp) return "強制登出、暫停帳號權限，並進行二次身分驗證 (2FA)。";
        if (highFreq) return "檢查是否為自動化腳本或暴力破解攻擊 (Brute Force)。";
        return "發送異地登入安全通知給使用者。";
    }

    public static void main(String[] args) {
        LoginActivityReport reporter = new LoginActivityReport();

        List<LoginLog> rawLogs = new ArrayList<>();
        
        rawLogs.add(new LoginLog("alice", "192.168.1.10", "2026-03-31 08:00:00"));
        rawLogs.add(new LoginLog("alice", "192.168.1.10", "2026-03-31 12:30:00"));

        rawLogs.add(new LoginLog("bob", "10.0.0.1", "2026-03-31 09:00:00"));
        rawLogs.add(new LoginLog("bob", "10.0.0.1", "2026-03-31 09:00:05"));
        rawLogs.add(new LoginLog("bob", "10.0.0.1", "2026-03-31 09:00:10"));
        rawLogs.add(new LoginLog("bob", "10.0.0.1", "2026-03-31 09:00:15"));
        rawLogs.add(new LoginLog("bob", "10.0.0.1", "2026-03-31 09:00:20"));

        rawLogs.add(new LoginLog("charlie", "140.112.1.1", "2026-03-31 10:00:00"));
        rawLogs.add(new LoginLog("charlie", "211.75.3.2",   "2026-03-31 10:15:00"));
        rawLogs.add(new LoginLog("charlie", "61.216.9.8",   "2026-03-31 10:30:00"));

        rawLogs.add(new LoginLog("david", "172.16.0.5", "2026-03-31 11:00:00"));

        reporter.processLogs(rawLogs);
        reporter.printGeneralReport();
        reporter.printAnomalyReport(4, 3);
    }    
}
