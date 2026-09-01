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

