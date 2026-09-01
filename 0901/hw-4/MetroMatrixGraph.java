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

  public List<String> getNeighbors(String station) {
        Integer u = stationMap.get(station);
        List<String> neighbors = new ArrayList<>();

        if (u == null) {
            System.out.println("失敗,找不到站點: " + station);
            return neighbors;
        }

        for (int v = 0; v < numStations; v++) {
            if (matrix[u][v] == 1) {
                neighbors.add(stationNames[v]);
            }
        }
        return neighbors;
    }

    public int getDegree(String station) {
        Integer u = stationMap.get(station);
        if (u == null) {
            return -1;
        }

        int degree = 0;
        for (int v = 0; v < numStations; v++) {
            if (matrix[u][v] == 1) {
                degree++;
            }
        }
        return degree;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void matrixReport() {
        System.out.println("                       Adjacency Matrix");
        System.out.printf(" 站點總數Vertices: %d  總連通邊數Edges: %d%n", numStations, edgeCount);

        System.out.printf("%-12s", "Station");
        for (String name : stationNames) {
            System.out.printf("%-8s", name);
        }
        System.out.println("\n" + "-".repeat(12 + numStations * 8));

        for (int i = 0; i < numStations; i++) {
            System.out.printf("%-12s", stationNames[i]);
            for (int j = 0; j < numStations; j++) {
                System.out.printf("%-8d", matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println(" 各站點Degree與鄰接站點明細:");
        for (String station : stationNames) {
            List<String> neighbors = getNeighbors(station);
            System.out.printf("  • %-8s  Degree: %d  鄰站: %s%n",
                    station, neighbors.size(), neighbors);
        }
    }

  public static void main(String[] args) {
        String[] metroStations = {
            "台北車站", "中山", "西門", "忠孝新生", "大安", "南京復興"
        };

        MetroMatrixGraph graph = new MetroMatrixGraph(metroStations);

        System.out.println("開始建置捷運路線圖");
        graph.addEdge("台北車站", "中山");
        graph.addEdge("台北車站", "大安");

        graph.addEdge("台北車站", "西門");
        graph.addEdge("台北車站", "忠孝新生");

        graph.addEdge("西門", "中山");
        graph.addEdge("中山", "南京復興");

        graph.addEdge("南京復興", "忠孝新生");
        graph.addEdge("忠孝新生", "大安");

        graph.matrixReport();

        System.out.println("測試特定站點查詢功能");
        String queryStation = "台北車站";
        System.out.println("查詢站點: " + queryStation);
        System.out.println("  鄰站清單: " + graph.getNeighbors(queryStation));
        System.out.println("  分支度 (Degree): " + graph.getDegree(queryStation));
        System.out.println("  全圖總邊數 (Total Edge Count): " + graph.getEdgeCount());
    }
}
