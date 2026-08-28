import java.util.ArrayList;
import java.util.List;

class Order {
    private final String orderId;
    private String customerName;
    private double amount;

    public Order(String orderId, String customerName, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = Math.max(0.0, amount);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = Math.max(0.0, amount);
    }

    @Override
    public String toString() {
        return String.format("[%s] 顧客: %-10s | 金額: $%,10.2f", orderId, customerName, amount);
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

public class OrderBstSystem {
  private OrderNode root;

    public OrderBstSystem() {
        this.root = null;
    }

    public boolean addOrder(Order order) {
        if (order == null || order.getOrderId() == null || order.getOrderId().isBlank()) {
            System.out.println("新增失敗,無效的訂單資料");
            return false;
        }

        if (findOrder(order.getOrderId()) != null) {
            System.out.println("失敗,訂單編號 [" + order.getOrderId() + "] 已存在，不可重複新增");
            return false;
        }

        root = insertHelper(root, order);
        System.out.println("成功建立訂單: " + order);
        return true;
    }

    private OrderNode insertHelper(OrderNode node, Order order) {
        if (node == null) {
            return new OrderNode(order);
        }

        int cmp = order.getOrderId().compareTo(node.order.getOrderId());
        if (cmp < 0) {
            node.left = insertHelper(node.left, order);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, order);
        }
        return node;
    }
}
