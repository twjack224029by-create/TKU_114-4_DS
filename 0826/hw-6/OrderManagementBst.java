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


