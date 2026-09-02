import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CoursePlanningGraph {

    private final Map<String, String> courses;
    
    private final Map<String, List<String>> prerequisiteGraph;

    public CoursePlanningGraph() {
        this.courses = new HashMap<>();
        this.prerequisiteGraph = new LinkedHashMap<>();
    }

    public void addCourse(String id, String name) {
        if (id == null || name == null) return;
        courses.put(id, name);
        prerequisiteGraph.putIfAbsent(id, new ArrayList<>());
    }

    public void addPrerequisite(String prereqCourseId, String dependentCourseId) {
        if (!courses.containsKey(prereqCourseId) || !courses.containsKey(dependentCourseId)) {
            System.out.printf("課程不存在: %s 或 %s%n", prereqCourseId, dependentCourseId);
            return;
        }

        List<String> dependents = prerequisiteGraph.get(prereqCourseId);
        if (!dependents.contains(dependentCourseId)) {
            dependents.add(dependentCourseId);
        }
    }

    public boolean isReachable(String sourceId, String targetId) {
        if (sourceId == null || targetId == null || 
            !courses.containsKey(sourceId) || !courses.containsKey(targetId)) {
            return false;
        }

        if (sourceId.equals(targetId)) return true;

        Set<String> visited = new HashSet<>();
        return dfsCheckReachable(sourceId, targetId, visited);
    }

    private boolean dfsCheckReachable(String current, String target, Set<String> visited) {
        visited.add(current);

        for (String neighbor : prerequisiteGraph.getOrDefault(current, List.of())) {
            if (neighbor.equals(target)) {
                return true;
            }
            if (!visited.contains(neighbor)) {
                if (dfsCheckReachable(neighbor, target, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getAllAffectedCourses(String baseCourseId) {
        if (baseCourseId == null || !courses.containsKey(baseCourseId)) {
            return List.of();
        }

        Set<String> visited = new HashSet<>();
        List<String> affectedList = new ArrayList<>();

        dfsCollectAffected(baseCourseId, visited, affectedList, true);

        return affectedList;
    }

    private void dfsCollectAffected(String current, Set<String> visited, List<String> affectedList, boolean isRoot) {
        visited.add(current);

        if (!isRoot) {
            affectedList.add(current);
        }

        for (String neighbor : prerequisiteGraph.getOrDefault(current, List.of())) {
            if (!visited.contains(neighbor)) {
                dfsCollectAffected(neighbor, visited, affectedList, false);
            }
        }
    }

    public void printAffectedReport(String baseCourseId) {
        String courseName = courses.getOrDefault(baseCourseId, baseCourseId);
        System.out.printf("分析 若變動/修畢課程 [%s (%s)]，受影響的下游課程清單：%n", courseName, baseCourseId);

        if (!courses.containsKey(baseCourseId)) {
            System.out.println("錯誤：找不到課程ID。");
            return;
        }

        List<String> affected = getAllAffectedCourses(baseCourseId);

        if (affected.isEmpty()) {
            System.out.println("無後續課程。");
        } else {
            System.out.printf("共影響 %d 門後續課程：%n", affected.size());
            for (String id : affected) {
                System.out.printf("    %s (%s)%n", courses.get(id), id);
            }
        }
    }

  
}
