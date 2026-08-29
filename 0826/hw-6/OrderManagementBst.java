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

    public Order find(int orderId) {
        OrderNode node = findRecursive(root, orderId);
        return (node != null) ? node.order : null;
    }

    private OrderNode findRecursive(OrderNode current, int orderId) {
        if (current == null) return null;
        if (orderId == current.order.getOrderId()) return current;
        return (orderId < current.order.getOrderId()) 
                ? findRecursive(current.left, orderId) 
                : findRecursive(current.right, orderId);
    }

    public boolean updateStatus(int orderId, OrderStatus newStatus) {
        Order order = find(orderId);
        if (order == null) {
            System.out.println("失敗,查無訂單編號 #" + orderId);
            return false;
        }
        OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);
        System.out.println("更新,訂單編號 #" + orderId + " 狀態由 " + oldStatus + " 變更為 " + newStatus);
        return true;
    }

    public boolean cancel(int orderId) {
        Order order = find(orderId);
        if (order == null) {
            System.out.println("失敗,查無訂單編號 #" + orderId);
            return false;
        }
        if (order.getStatus() == OrderStatus.COMPLETED) {
            System.out.println("失敗,訂單編號 #" + orderId + " 已完成交易，無法取消");
            return false;
        }
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println("成功,訂單編號 #" + orderId + " 已變更狀態為 CANCELLED");
        return true;
    }

    public boolean remove(int orderId) {
        Order order = find(orderId);
        if (order == null) {
            System.out.println("失敗,查無訂單編號 #" + orderId);
            return false;
        }

        if (order.getStatus() != OrderStatus.CANCELLED) {
            System.out.println("失敗,訂單編號 #" + orderId + " 當前狀態為 " 
                    + order.getStatus() + "，只有CANCELLED訂單才可刪除");
            return false;
        }

        root = removeRecursive(root, orderId);
        System.out.println("成功,已自系統中永久移除取消的訂單編號 #" + orderId);
        return true;
    }

    private OrderNode removeRecursive(OrderNode current, int orderId) {
        if (current == null) return null;

        if (orderId < current.order.getOrderId()) {
            current.left = removeRecursive(current.left, orderId);
        } else if (orderId > current.order.getOrderId()) {
            current.right = removeRecursive(current.right, orderId);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            OrderNode minNode = findMin(current.right);
            current.order = minNode.order;
            current.right = removeRecursive(current.right, minNode.order.getOrderId());
        }
        return current;
    }

    private OrderNode findMin(OrderNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void idRangeReport(int minId, int maxId) {
        System.out.println(String.format("訂單ID範圍報表 (範圍: %d ~ %d)", minId, maxId));
        List<Order> result = new ArrayList<>();
        idRangeSearchRecursive(root, minId, maxId, result);

        if (result.isEmpty()) {
            System.out.println("  (該範圍內無任何訂單)");
        } else {
            for (Order o : result) {
                System.out.println("  " + o);
            }
        }
    }

    private void idRangeSearchRecursive(OrderNode current, int minId, int maxId, List<Order> result) {
        if (current == null) return;

        int id = current.order.getOrderId();

        if (id > minId) {
            idRangeSearchRecursive(current.left, minId, maxId, result);
        }

        if (id >= minId && id <= maxId) {
            result.add(current.order);
        }

        if (id < maxId) {
            idRangeSearchRecursive(current.right, minId, maxId, result);
        }
    }

    public double getTotalAmount() {
        return calculateTotalAmountRecursive(root);
    }

    private double calculateTotalAmountRecursive(OrderNode current) {
        if (current == null) return 0.0;
        
        return current.order.getAmount() 
                + calculateTotalAmountRecursive(current.left) 
                + calculateTotalAmountRecursive(current.right);
    }

    public static void main(String[] args) {
        OrderManagementBst system = new OrderManagementBst();

        System.out.println("測試訂單建立與金額邊界驗證");
        system.add(1005, "Alice", 1250.0);
        system.add(1002, "Bob", 300.5);
        system.add(1008, "Charlie", 4500.0);
        system.add(1001, "David", 890.0);
        system.add(1006, "Eve", 2100.0);
        
        system.add(1009, "Frank", -500.0); 

        System.out.println("\n全系統總金額統計");
        System.out.printf("目前系統總訂單金額為: $%.2f%n", system.getTotalAmount());

        System.out.println("\n測試狀態更新與取消");
        system.updateStatus(1005, OrderStatus.PAID);
        system.updateStatus(1002, OrderStatus.SHIPPED);
        system.cancel(1001); 

        System.out.println("\n測試刪除約定制約 (Remove Constraint)");
        system.remove(1005);
        
        system.remove(1001);

        System.out.println("\nID Range Report");
        system.idRangeReport(1002, 1007);

        System.out.println("刪除後的最終總金額統計");
        System.out.printf("更新後系統總訂單金額為: $%.2f%n", system.getTotalAmount());
    }

}

