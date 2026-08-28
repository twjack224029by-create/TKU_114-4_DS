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

    public Order findOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) return null;
        OrderNode node = searchHelper(root, orderId);
        return (node != null) ? node.order : null;
    }

    private OrderNode searchHelper(OrderNode node, String orderId) {
        if (node == null) return null;

        int cmp = orderId.compareTo(node.order.getOrderId());
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return searchHelper(node.left, orderId);
        } else {
            return searchHelper(node.right, orderId);
        }
    }

    public boolean cancelOrder(String orderId) {
        if (findOrder(orderId) == null) {
            System.out.println("失敗,找不到訂單編號 [" + orderId + "]");
            return false;
        }

        root = deleteHelper(root, orderId);
        System.out.println("成功取消訂單: 編號 [" + orderId + "]");
        return true;
    }

    private OrderNode deleteHelper(OrderNode node, String orderId) {
        if (node == null) return null;

        int cmp = orderId.compareTo(node.order.getOrderId());
        if (cmp < 0) {
            node.left = deleteHelper(node.left, orderId);
        } else if (cmp > 0) {
            node.right = deleteHelper(node.right, orderId);
        } else {

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            OrderNode minNode = getMin(node.right);
            node.order = minNode.order;
            node.right = deleteHelper(node.right, minNode.order.getOrderId());
        }
        return node;
    }

    private OrderNode getMin(OrderNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

     public boolean updateAmount(String orderId, double newAmount) {
        if (newAmount < 0) {
            System.out.println("失敗,訂單金額不可為負數");
            return false;
        }

        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("失敗,找不到訂單編號 [" + orderId + "]");
            return false;
        }

        double oldAmount = order.getAmount();
        order.setAmount(newAmount);
        System.out.printf("成功更新訂單 [%s] 金額: $%,10.2f ➔ $%,10.2f%n", orderId, oldAmount, newAmount);
        return true;
    }

    public void printRangeReport(String startId, String endId) {
        System.out.println("\n訂單報表 [" + startId + " ~ " + endId + "]");
        List<Order> list = new ArrayList<>();
        rangeSearchHelper(root, startId, endId, list);

        if (list.isEmpty()) {
            System.out.println("該區間內無任何訂單紀錄");
        } else {
            for (Order ord : list) {
                System.out.println("  " + ord);
            }
        }
    }

    private void rangeSearchHelper(OrderNode node, String startId, String endId, List<Order> list) {
        if (node == null) return;

        if (node.order.getOrderId().compareTo(startId) > 0) {
            rangeSearchHelper(node.left, startId, endId, list);
        }

        if (node.order.getOrderId().compareTo(startId) >= 0 && node.order.getOrderId().compareTo(endId) <= 0) {
            list.add(node.order);
        }

        if (node.order.getOrderId().compareTo(endId) < 0) {
            rangeSearchHelper(node.right, startId, endId, list);
        }
    }

    public void printSummary() {
        List<Order> allOrders = new ArrayList<>();
        inOrderHelper(root, allOrders);

        double totalAmount = 0.0;
        for (Order o : allOrders) {
            totalAmount += o.getAmount();
        }

        System.out.println("\nSummary");
        System.out.println("  * 總訂單筆數 : " + allOrders.size() + " 筆");
        System.out.printf("  * 總營運金額 : $%,.2f%n", totalAmount);
        if (allOrders.isEmpty()) {
            System.out.println("目前無任何訂單");
        } else {
            for (Order o : allOrders) {
                System.out.println("  " + o);
            }
        }
    }

    private void inOrderHelper(OrderNode node, List<Order> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.order);
        inOrderHelper(node.right, list);
    }

    public static void main(String[] args) {
        System.out.println("OrderBstSystem\n");

        OrderBstSystem system = new OrderBstSystem();

        System.out.println("建立新訂單");
        system.addOrder(new Order("ORD-103", "Alice", 1250.0));
        system.addOrder(new Order("ORD-101", "Bob", 3200.0));
        system.addOrder(new Order("ORD-105", "Charlie", 850.5));
        system.addOrder(new Order("ORD-102", "David", 4500.0));
        system.addOrder(new Order("ORD-104", "Eve", 2100.0));

        System.out.println("\n重複訂單新增測試");
        system.addOrder(new Order("ORD-103", "Duplicate Alice", 9999.0));

        system.printSummary();

        System.out.println("查詢訂單");
        Order ord = system.findOrder("ORD-102");
        System.out.println("查詢 ORD-102 結果: " + (ord != null ? ord : "查無此訂單"));

        Order notFound = system.findOrder("ORD-999");
        System.out.println("查詢 ORD-999 結果: " + (notFound != null ? notFound : "查無此訂單"));

        System.out.println("\n更新訂單金額");
        system.updateAmount("ORD-101", 3800.0); 
        system.updateAmount("ORD-999", 500.0); 

        System.out.println("\n區間報表測試ORD-102 ~ ORD-104");
        system.printRangeReport("ORD-102", "ORD-104");

        System.out.println("取消訂單測試");
        system.cancelOrder("ORD-102"); 

        system.printSummary();
    } 
}
