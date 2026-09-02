import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CampusNavigationSystem {
  private final Map<String, String> locations;
    private final Map<String, List<String>> roadNetwork;

    public CampusNavigationSystem() {
        this.locations = new HashMap<>();
        this.roadNetwork = new LinkedHashMap<>();
    }

    public void addLocation(String id, String name) {
        if (id == null || name == null) return;
        locations.put(id, name);
        roadNetwork.putIfAbsent(id, new ArrayList<>());
    }

    public void addRoad(String loc1, String loc2) {
        if (!locations.containsKey(loc1) || !locations.containsKey(loc2)) {
            System.out.printf("失敗,指定地點不存在: %s 或 %s%n", loc1, loc2);
            return;
        }

        List<String> neighbors1 = roadNetwork.get(loc1);
        if (!neighbors1.contains(loc2)) neighbors1.add(loc2);

        List<String> neighbors2 = roadNetwork.get(loc2);
        if (!neighbors2.contains(loc1)) neighbors2.add(loc1);
    }

  public List<String> findShortestPath(String startId, String targetId) {
        if (startId == null || targetId == null || 
            !locations.containsKey(startId) || !locations.containsKey(targetId)) {
            return List.of();
        }

        if (startId.equals(targetId)) {
            return List.of(startId);
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>(); 

        queue.offer(startId);
        visited.add(startId);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(targetId)) {
                found = true;
                break;
            }

            for (String neighbor : roadNetwork.getOrDefault(current, List.of())) {
                if (locations.containsKey(neighbor) && visited.add(neighbor)) {
                    previous.put(neighbor, current); 
                    queue.offer(neighbor);
                }
            }
        }

        if (!found && !visited.contains(targetId)) {
            return List.of();
        }

        List<String> path = new ArrayList<>();
        for (String at = targetId; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

  public void printNavigation(String startId, String targetId) {
        String startName = locations.getOrDefault(startId, startId);
        String targetName = locations.getOrDefault(targetId, targetId);

        System.out.printf("查詢 從 [%s (%s)] 到 [%s (%s)]%n", startName, startId, targetName, targetId);

        List<String> pathIds = findShortestPath(startId, targetId);

        if (pathIds.isEmpty()) {
            System.out.println("搜尋結果: 無法到達目的地或指定地點無效。");
        } else {
            List<String> pathNames = new ArrayList<>();
            for (String id : pathIds) {
                pathNames.add(locations.get(id) + " (" + id + ")");
            }
            System.out.printf("最優路線 (經過 %d 個地點, %d 段道路):%n", pathIds.size(), pathIds.size() - 1);
            System.out.println("   " + String.join(" ➔ ", pathNames));
        }
    }
    public static void main(String[] args) {
        CampusNavigationSystem campus = new CampusNavigationSystem();

        System.out.println("HashMap保存地點");
        campus.addLocation("GATE", "大門口");
        campus.addLocation("LIB", "中央圖書館");
        campus.addLocation("ENG", "工程館");
        campus.addLocation("BUS", "商學館");
        campus.addLocation("GYM", "綜合體育館");
        campus.addLocation("DORM", "學生宿舍");
        campus.addLocation("ISLAND", "湖心亭孤島"); 

        System.out.println("\n建立道路網絡");
        campus.addRoad("GATE", "LIB");
        campus.addRoad("GATE", "BUS");
        campus.addRoad("LIB", "ENG");
        campus.addRoad("BUS", "ENG");
        campus.addRoad("BUS", "GYM");
        campus.addRoad("ENG", "DORM");
        campus.addRoad("GYM", "DORM");

        System.out.println("\n測試路線搜尋");
        
        campus.printNavigation("GATE", "DORM");

        campus.printNavigation("GATE", "GYM");

        campus.printNavigation("LIB", "LIB");

        campus.printNavigation("GATE", "ISLAND");

        campus.printNavigation("GATE", "UNKNOWN");
    }
}
