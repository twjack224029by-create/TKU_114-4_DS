import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Edge {
    private String targetVertex;
    private double cost; 

    public Edge(String targetVertex, double cost) {
        this.targetVertex = targetVertex;
        this.cost = cost;
    }

    public String getTargetVertex() { return targetVertex; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(targetVertex, edge.targetVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetVertex);
    }

    @Override
    public String toString() {
        return String.format("%s(成本: %.1f)", targetVertex, cost);
    }
}

public class LogisticsWeightedGraph {

    private Map<String, List<Edge>> adjList;

    public LogisticsWeightedGraph() {
        this.adjList = new HashMap<>();
    }

    public boolean addVertex(String vertex) {
        if (vertex == null || vertex.trim().isEmpty()) return false;
        if (!adjList.containsKey(vertex)) {
            adjList.put(vertex, new ArrayList<>());
            return true;
        }
        return false;
    }

    public boolean containsVertex(String vertex) {
        return adjList.containsKey(vertex);
    }

    public boolean addOrUpdateEdge(String source, String destination, double cost) {
        if (cost < 0) {
            System.out.printf("失敗,物流成本/權重不可為負(來源: %s ➔ 目的地: %s, 傳入成本: %.1f)%n", 
                    source, destination, cost);
            return false;
        }

        if (!containsVertex(source) || !containsVertex(destination)) {
            System.out.printf("失敗,站點不存在(來源: \"%s\" [%s], 目的地: \"%s\" [%s])%n",
                    source, containsVertex(source) ? "存在" : "不存在",
                    destination, containsVertex(destination) ? "存在" : "不存在");
            return false;
        }

        List<Edge> neighbors = adjList.get(source);
        for (Edge edge : neighbors) {
            if (edge.getTargetVertex().equals(destination)) {
                double oldCost = edge.getCost();
                edge.setCost(cost);
                System.out.printf("更新成功,路線 %s ➔ %s 成本由 %.1f 更新為 %.1f%n", 
                        source, destination, oldCost, cost);
                return true;
            }
        }

        neighbors.add(new Edge(destination, cost));
        System.out.printf("新增邊成功 路線 %s ➔ %s (成本: %.1f)%n", source, destination, cost);
        return true;
    }

    public boolean removeEdge(String source, String destination) {
        if (!containsVertex(source) || !containsVertex(destination)) {
            System.out.printf("失敗,指定站點不存在: %s ➔ %s%n", source, destination);
            return false;
        }

        List<Edge> neighbors = adjList.get(source);
        boolean removed = neighbors.removeIf(edge -> edge.getTargetVertex().equals(destination));

        if (removed) {
            System.out.printf(" 已刪除物流路線: %s ➔ %s%n", source, destination);
        } else {
            System.out.printf("失敗,查無路線: %s ➔ %s%n", source, destination);
        }
        return removed;
    }

    public Double getEdgeCost(String source, String destination) {
        if (!containsVertex(source) || !containsVertex(destination)) {
            return null;
        }

        for (Edge edge : adjList.get(source)) {
            if (edge.getTargetVertex().equals(destination)) {
                return edge.getCost();
            }
        }
        return null;
    }

    public List<Edge> getOutgoingEdges(String vertex) {
        if (!containsVertex(vertex)) return Collections.emptyList();
        return new ArrayList<>(adjList.get(vertex));
    }

    public void printReport() {
        System.out.println("                     Weighted Graph報表");
        System.out.printf(" 站點總數: %d  總路線數 (Edges): %d%n", adjList.size(), getTotalEdgesCount());

        List<String> vertices = new ArrayList<>(adjList.keySet());
        Collections.sort(vertices);

        for (String v : vertices) {
            List<Edge> edges = adjList.get(v);
            System.out.printf(" 站點 [%-10s] ➔ 可達目的地 (%d 條): %s%n", 
                    v, edges.size(), edges.isEmpty() ? "(無出發路線)" : edges);
        }
    }

    private int getTotalEdgesCount() {
        int count = 0;
        for (List<Edge> edges : adjList.values()) {
            count += edges.size();
        }
        return count;
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph graph = new LogisticsWeightedGraph();

        System.out.println("初始化站點");
        String[] hubs = {"台北總倉", "桃園轉運站", "台中轉運站", "高雄轉運站", "花蓮分倉"};
        for (String hub : hubs) {
            graph.addVertex(hub);
        }

        System.out.println("\n新增與更新路線");
        graph.addOrUpdateEdge("台北總倉", "桃園轉運站", 150.0);
        graph.addOrUpdateEdge("台北總倉", "台中轉運站", 350.0);
        graph.addOrUpdateEdge("桃園轉運站", "台中轉運站", 200.0);
        graph.addOrUpdateEdge("台中轉運站", "高雄轉運站", 300.0);
        graph.addOrUpdateEdge("台北總倉", "花蓮分倉", 400.0);

        graph.addOrUpdateEdge("台北總倉", "台中轉運站", 320.0);

        System.out.println("\n測試防呆 (負&不存在點)");
        graph.addOrUpdateEdge("台中轉運站", "高雄轉運站", -50.0);
        graph.addOrUpdateEdge("新竹轉運站", "台中轉運站", 100.0);
        graph.addOrUpdateEdge("台北總倉", "台東分倉", 500.0);

        graph.printReport();

        System.out.println("測試單點查詢與路線移除");
        Double cost = graph.getEdgeCost("台北總倉", "台中轉運站");
        System.out.println("查詢 台北總倉 ➔ 台中轉運站 成本: " + cost);

        graph.removeEdge("台北總倉", "花蓮分倉");
        graph.removeEdge("台北總倉", "高雄轉運站"); 

        graph.printReport();
    }
}
