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



}
