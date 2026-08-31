import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Product {
    private String id;
    private int sales;

    public Product(String id, int sales) {
        this.id = id;
        this.sales = sales;
    }

    public String getId() { return id; }
    public int getSales() { return sales; }
    public void addSales(int amount) { this.sales += amount; }

    @Override
    public String toString() {
        return String.format("商品 ID: %-10s  總銷量: %d", id, sales);
    }
}

public class TopSellingProducts {
  private Map<String, Integer> salesMap; 

    public TopSellingProducts() {
        this.salesMap = new HashMap<>();
    }

    public void recordSale(String id, int sales) {
        if (sales <= 0) {
            System.out.println("無視 商品 " + id + " 銷量增加值必須大於 0 (" + sales + ")");
            return;
        }
        salesMap.put(id, salesMap.getOrDefault(id, 0) + sales);
    }

    public List<Product> getTopK(int k) {
        if (k <= 0 || salesMap.isEmpty()) {
            return new ArrayList<>();
        }

        PriorityQueue<Product> minHeap = new PriorityQueue<>((p1, p2) -> {
            if (p1.getSales() != p2.getSales()) {
                return Integer.compare(p1.getSales(), p2.getSales());
            }
            return p2.getId().compareTo(p1.getId());
        });

        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            Product product = new Product(entry.getKey(), entry.getValue());
            minHeap.offer(product);

            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        List<Product> topKList = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            topKList.add(minHeap.poll());
        }

        Collections.sort(topKList, (p1, p2) -> {
            if (p1.getSales() != p2.getSales()) {
                return Integer.compare(p2.getSales(), p1.getSales()); 
            }
            return p1.getId().compareTo(p2.getId()); 
        });

        return topKList;
    }

    public void clear() {
        salesMap.clear();
    }
  
}
