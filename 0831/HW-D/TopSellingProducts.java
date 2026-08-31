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
  
}
