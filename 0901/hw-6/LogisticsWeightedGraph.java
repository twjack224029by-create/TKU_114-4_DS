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



}
