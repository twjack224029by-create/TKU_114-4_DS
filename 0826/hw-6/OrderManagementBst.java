import java.util.ArrayList;
import java.util.List;

enum OrderStatus {
    PENDING,    
    PAID,       
    SHIPPED,    
    COMPLETED,  
    CANCELLED   
}

class Order {
    private int orderId;
    private String customer;
    private double amount;
    private OrderStatus status;

    public Order(int orderId, String customer, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("金額不可為負");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.status = OrderStatus.PENDING; 
    }

    public int getOrderId() { return orderId; }
    public String getCustomer() { return customer; }
    public double getAmount() { return amount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("訂單編號: %-5d  客戶名稱: %-10s  金額: $%8.2f  狀態: %-9s",
                orderId, customer, amount, status);
    }
}

class OrderNode {
    Order order;
    OrderNode left;
    OrderNode right;

    public OrderNode(Order order) {
        this.order = order;
        this.left = null;
        this.right = null;
    }
}

public class OrderManagementBst {
    private OrderNode root;

    public OrderManagementBst() {
        this.root = null;
    }

    public boolean add(int orderId, String customer, double amount) {
        if (amount < 0) {
            System.out.println("失敗,訂單編號 #" + orderId + " 金額 ($" + amount + ") 不可為負");
            return false;
        }
        if (find(orderId) != null) {
            System.out.println("失敗,訂單編號 #" + orderId + " 已存在");
            return false;
        }

        Order newOrder = new Order(orderId, customer, amount);
        root = addRecursive(root, newOrder);
        System.out.println("新增成功 " + newOrder);
        return true;
    }

    private OrderNode addRecursive(OrderNode current, Order order) {
        if (current == null) {
            return new OrderNode(order);
        }
        if (order.getOrderId() < current.order.getOrderId()) {
            current.left = addRecursive(current.left, order);
        } else if (order.getOrderId() > current.order.getOrderId()) {
            current.right = addRecursive(current.right, order);
        }
        return current;
    }


}

