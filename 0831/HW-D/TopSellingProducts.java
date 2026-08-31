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

    public static void main(String[] args) {
        TopSellingProducts tracker = new TopSellingProducts();

        System.out.println("                TopSellingProducts");

        System.out.println("模擬輸入包含重複 ID 的多筆交易數據");

        Object[][] rawTransactions = {
            {"A", 150},
            {"B", 300},
            {"C", 50},
            {"A", 200}, 
            {"D", 300}, 
            {"E", 500}, 
            {"F", 100},
            {"B", 50},  
            {"G", 350} 
        };

        for (Object[] tx : rawTransactions) {
            String id = (String) tx[0];
            int sales = (Integer) tx[1];
            tracker.recordSale(id, sales);
            System.out.printf("  [交易紀錄] 商品: %-8s +%-3d 件%n", id, sales);
        }

        System.out.println("銷量合併預期結果說明：");
        System.out.println("  E: 500");
        System.out.println("  A: 350 ");
        System.out.println("  B: 350");
        System.out.println("  G: 350");
        System.out.println("  D: 300");

        System.out.println("測試前3熱門商品");
        List<Product> top3 = tracker.getTopK(3);
        printReport(top3, 3);

        System.out.println("測試前5熱門商品");
        List<Product> top5 = tracker.getTopK(5);
        printReport(top5, 5);

        System.out.println("測試K>商品種類數");
        List<Product> top10 = tracker.getTopK(10);
        printReport(top10, 10);
    }

    private static void printReport(List<Product> products, int k) {
        System.out.println("前" + k + " 排行榜結果");
        if (products.isEmpty()) {
            System.out.println("  (無資料)");
        } else {
            for (int i = 0; i < products.size(); i++) {
                System.out.printf("  Rank %02d: %s%n", (i + 1), products.get(i));
            }
        }
        System.out.println();
    }
}
