import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroMatrixGraph {
  private String[] stationNames;          
    private Map<String, Integer> stationMap; 
    private int[][] matrix;               
    private int numStations;                
    private int edgeCount;                

    public MetroMatrixGraph(String[] stations) {
        if (stations == null || stations.length == 0) {
            throw new IllegalArgumentException("站點清單不可為空");
        }
        this.numStations = stations.length;
        this.stationNames = stations.clone();
        this.stationMap = new HashMap<>();
        this.matrix = new int[numStations][numStations];
        this.edgeCount = 0;

        for (int i = 0; i < numStations; i++) {
            stationMap.put(stations[i], i);
        }
    }

    public void addEdge(String stationA, String stationB) {
        Integer u = stationMap.get(stationA);
        Integer v = stationMap.get(stationB);

        if (u == null || v == null) {
            System.out.println("失敗,包含未定義的站點: " + stationA + " 或 " + stationB);
            return;
        }

        if (u.equals(v)) {
            System.out.println("不支援Self-loop: " + stationA);
            return;
        }

        if (matrix[u][v] == 0) {
            matrix[u][v] = 1;
            matrix[v][u] = 1; 
            edgeCount++;
            System.out.printf("連通成功 %s  %s%n", stationA, stationB);
        }
    }
}
