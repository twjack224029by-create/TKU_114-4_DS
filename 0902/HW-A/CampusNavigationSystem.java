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
    
}
