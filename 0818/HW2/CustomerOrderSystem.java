import java.util.Arrays;

class Customer {
    private final String customerId;
    private final String name;
    private final String email;

    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class OrderItem {
    private final String itemName;
    private final double price;
    private final int quantity;

    public OrderItem(String itemName, double price, int quantity) {
        if (price < 0 || quantity <= 0) {
            throw new IllegalArgumentException("價格不能為負且數量必須大於 0");
        }
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return price * quantity;
    }
}

class CustomerOrder {
    private final String orderId;
    private final Customer customer;      
    private final OrderItem[] items;     
    private int itemCount;               

    public CustomerOrder(String orderId, Customer customer, int maxItems) {
        if (customer == null) {
            throw new IllegalArgumentException("訂單必須包含有效的顧客資料");
        }
        if (maxItems <= 0) {
            throw new IllegalArgumentException("訂單品項容量必須大於 0");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.items = new OrderItem[maxItems];
        this.itemCount = 0;
    }

    public boolean addItem(OrderItem item) {
        if (item == null) {
            System.out.println("失敗,品項不能為空");
            return false;
        }
        if (itemCount >= items.length) {
            System.out.println("失敗,訂單容量已滿，無法新增品項: " + item.getItemName());
            return false;
        }
        items[itemCount] = item;
        itemCount++;
        return true;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

    public int calculateTotalQuantity() {
        int totalQty = 0;
        for (int i = 0; i < itemCount; i++) {
            totalQty += items[i].getQuantity();
        }
        return totalQty;
    }


    public void printOrderSummary() {
        System.out.println("摘 要");
        System.out.println("訂單編號: " + orderId);
        System.out.println("顧客姓名: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("訂單明細:");
        
        if (itemCount == 0) {
            System.out.println("  (無品項)");
        } else {
            for (int i = 0; i < itemCount; i++) {
                OrderItem item = items[i];
                System.out.printf("  %d. %-12s | 單價: $%8.2f | 數量: %2d | 小計: $%8.2f%n",
                        (i + 1), item.getItemName(), item.getPrice(), item.getQuantity(), item.getSubtotal());
            }
        }
        
        System.out.printf("購買品項種類: %d 項%n", itemCount);
        System.out.printf("購買商品總數: %d 件%n", calculateTotalQuantity());
        System.out.printf("訂單總金額:   $%.2f%n", calculateTotalAmount());
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C001", "張小明", "ming@example.com");

        CustomerOrder order = new CustomerOrder("ORD-20260818-01", customer, 3);

        System.out.println("=== 開始新增品項 ===");
        order.addItem(new OrderItem("紙", 680.0, 1));
        order.addItem(new OrderItem("隻因", 450.0, 2));
        order.addItem(new OrderItem("筆", 80.0, 5));

        order.addItem(new OrderItem("USB", 300.0, 1));

        order.printOrderSummary();
    }
}
