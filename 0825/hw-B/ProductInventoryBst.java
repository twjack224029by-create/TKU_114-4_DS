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

    public boolean insert(Product product) {
        if (product == null || product.getProductId() == null) {
            System.out.println("失敗,無效的商品資料");
            return false;
        }

        if (searchById(product.getProductId()) != null) {
            System.out.println("失敗,商品編號 [" + product.getProductId() + "] 已存在，不得重複新增");
            return false;
        }

        root = insertHelper(root, product);
        System.out.println("成功上架商品: " + product);
        return true;
    }

    private ProductNode insertHelper(ProductNode node, Product product) {
        if (node == null) {
            return new ProductNode(product);
        }

        int cmp = product.getProductId().compareTo(node.product.getProductId());
        if (cmp < 0) {
            node.left = insertHelper(node.left, product);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, product);
        }
        return node;
    }

    public boolean restock(String productId, int quantity) {
        if (quantity <= 0) {
            System.out.println("失敗,補貨數量必須大於 0");
            return false;
        }

        Product product = searchById(productId);
        if (product == null) {
            System.out.println("失敗,找不到商品編號 [" + productId + "]");
            return false;
        }

        product.setStock(product.getStock() + quantity);
        System.out.println("補貨成功 [" + productId + " " + product.getName() + "]: 增加 " + quantity + " 件，目前庫存: " + product.getStock() + " 件");
        return true;
    }

    public boolean deduct(String productId, int quantity) {
        if (quantity <= 0) {
            System.out.println("失敗,扣除數量必須大於 0");
            return false;
        }

        Product product = searchById(productId);
        if (product == null) {
            System.out.println("失敗,找不到商品編號 [" + productId + "]");
            return false;
        }

        if (product.getStock() < quantity) {
            System.out.println("失敗 [" + productId + " " + product.getName() + "]: 庫存不足！現有: " + product.getStock() + " 件，欲扣除: " + quantity + " 件");
            return false;
        }

        product.setStock(product.getStock() - quantity);
        System.out.println("成功 [" + productId + " " + product.getName() + "]: 扣除 " + quantity + " 件，剩餘庫存: " + product.getStock() + " 件");
        return true;
    }

    public boolean delete(String productId) {
        if (searchById(productId) == null) {
            System.out.println("失敗,找不到商品編號 [" + productId + "]");
            return false;
        }

        root = deleteHelper(root, productId);
        System.out.println("成功下架商品: 編號 [" + productId + "]");
        return true;
    }

    private ProductNode deleteHelper(ProductNode node, String productId) {
        if (node == null) return null;

        int cmp = productId.compareTo(node.product.getProductId());
        if (cmp < 0) {
            node.left = deleteHelper(node.left, productId);
        } else if (cmp > 0) {
            node.right = deleteHelper(node.right, productId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            ProductNode minNode = getMin(node.right);
            node.product = minNode.product;
            node.right = deleteHelper(node.right, minNode.product.getProductId());
        }
        return node;
    }

    private ProductNode getMin(ProductNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void printInOrderReport() {
        List<Product> products = new ArrayList<>();
        inOrderHelper(root, products);

        System.out.println("\n 全館商品庫存報表");
        if (products.isEmpty()) {
            System.out.println("[ 目前無任何庫存商品資料 ]");
        } else {
            for (Product p : products) {
                System.out.println("  " + p);
            }
        }
    }

    private void inOrderHelper(ProductNode node, List<Product> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.product);
        inOrderHelper(node.right, list);
    }

    public static void main(String[] args) {
        System.out.println("ProductInventoryBst \n");

        ProductInventoryBst inventory = new ProductInventoryBst();

        System.out.println("商品上架測試");
        inventory.insert(new Product("P103", "無線滑鼠", 850, 50));
        inventory.insert(new Product("P101", "機械鍵盤", 2500, 20));
        inventory.insert(new Product("P105", "4K 螢幕", 12000, 10));
        inventory.insert(new Product("P102", "電競耳機", 1800, 30));
        inventory.insert(new Product("P104", "USB-C Hub", 990, 15));

        System.out.println("\n 測試重複 ID 上架");
        inventory.insert(new Product("P103", "重複的滑鼠", 500, 100));

        inventory.printInOrderReport();

        System.out.println("商品查詢測試");
        Product p = inventory.searchById("P102");
        System.out.println("查詢 P102 結果: " + (p != null ? p : "查無此商品"));

        Product pNotFound = inventory.searchById("P999");
        System.out.println("查詢 P999 結果: " + (pNotFound != null ? pNotFound : "查無此商品"));

        System.out.println("\n【步驟 4: 補貨與扣庫存測試】");
        inventory.restock("P101", 10); 
        inventory.deduct("P103", 20);  

        inventory.deduct("P105", 50);  

        System.out.println("\n 架刪除商品測試");
        inventory.delete("P104"); 
        inventory.delete("P103"); 

        inventory.printInOrderReport();
    }
}
