import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebsiteLinkGraph {

    private Map<String, List<String>> adjList;
    private Set<String> allPages;

    public WebsiteLinkGraph() {
        this.adjList = new HashMap<>();
        this.allPages = new HashSet<>();
    }

    public void addPage(String page) {
        allPages.add(page);
        adjList.putIfAbsent(page, new ArrayList<>());
    }

    public void addLink(String fromPage, String toPage) {
        addPage(fromPage);
        addPage(toPage);

        List<String> neighbors = adjList.get(fromPage);
        if (!neighbors.contains(toPage)) {
            neighbors.add(toPage);
            System.out.printf("連結建立 %s ➔ %s%n", fromPage, toPage);
        }
    }

    public List<String> getOutgoingLinks(String page) {
        return adjList.getOrDefault(page, Collections.emptyList());
    }

    public Map<String, Integer> getIncomingCounts() {
        Map<String, Integer> inCountMap = new HashMap<>();
        for (String page : allPages) {
            inCountMap.put(page, 0);
        }

        for (Map.Entry<String, List<String>> entry : adjList.entrySet()) {
            for (String target : entry.getValue()) {
                inCountMap.put(target, inCountMap.get(target) + 1);
            }
        }
        return inCountMap;
    }

    public List<String> getNoIncomingPages() {
        Map<String, Integer> inCounts = getIncomingCounts();
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : inCounts.entrySet()) {
            if (entry.getValue() == 0) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }

    public List<String> getNoOutgoingPages() {
        List<String> result = new ArrayList<>();
        for (String page : allPages) {
            List<String> outgoing = adjList.get(page);
            if (outgoing == null || outgoing.isEmpty()) {
                result.add(page);
            }
        }
        Collections.sort(result);
        return result;
    }

    public void printReport() {
        Map<String, Integer> incomingCounts = getIncomingCounts();

        System.out.println("                     網站連結有向圖分析表");
        System.out.printf(" 網頁總數: %d  總連結數 (Edges): %d%n", allPages.size(), getTotalEdgeCount());
        System.out.printf(" %-11s | %-14s | %-14s | %s%n", "網頁名稱", "Outgoing ", "Incoming ", "外連目標");


        List<String> sortedPages = new ArrayList<>(allPages);
        Collections.sort(sortedPages);

        for (String page : sortedPages) {
            List<String> outgoing = getOutgoingLinks(page);
            int inCount = incomingCounts.getOrDefault(page, 0);
            System.out.printf(" %-15s | %-14d | %-14d | %s%n",
                    page, outgoing.size(), inCount, outgoing);
        }

        System.out.println("特殊頁面分析結果:");
        
        List<String> noIncoming = getNoIncomingPages();
        System.out.println("  • 無 Incoming 頁面 (入口頁/孤立頁 In-Degree = 0): " 
                + (noIncoming.isEmpty() ? "(無)" : noIncoming));

        List<String> noOutgoing = getNoOutgoingPages();
        System.out.println("  • 無 Outgoing 頁面 (終點頁/死胡同 Out-Degree = 0): " 
                + (noOutgoing.isEmpty() ? "(無)" : noOutgoing));
    }

    private int getTotalEdgeCount() {
        int count = 0;
        for (List<String> targets : adjList.values()) {
            count += targets.size();
        }
        return count;
    }

    public static void main(String[] args) {
        
    }
    
}
