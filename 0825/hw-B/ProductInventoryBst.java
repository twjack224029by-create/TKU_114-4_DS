import java.util.ArrayList;
import java.util.List;

class Product {
    private final String productId;
    private String name;
    private int price;
    private int stock;

    public Product(String productId, String name, int price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = Math.max(0, stock);
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s | 單價: NT$%5d | 庫存: %3d 件", productId, name, price, stock);
    }
}

class ProductNode {
    Product product;
    ProductNode left;
    ProductNode right;

    public ProductNode(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
    }
}

public class ProductInventoryBst {
  private ProductNode root;

    public ProductInventoryBst() {
        this.root = null;
    }

    public Product searchById(String productId) {
        if (productId == null || productId.isBlank()) return null;
        ProductNode node = searchHelper(root, productId);
        return (node != null) ? node.product : null;
    }

    private ProductNode searchHelper(ProductNode node, String productId) {
        if (node == null) {
            return null;
        }

        int cmp = productId.compareTo(node.product.getProductId());
        if (cmp == 0) {
            return node; 
        } else if (cmp < 0) {
            return searchHelper(node.left, productId);
        } else {
            return searchHelper(node.right, productId);
        }
    }


}
