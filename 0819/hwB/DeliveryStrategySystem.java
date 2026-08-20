interface DeliveryMethod {
    String getMethodName();        
    int calculateFee(int weight);  
    String getEstimatedDays();     
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public String getMethodName() {
        return "黑貓宅配到府";
    }

    @Override
    public int calculateFee(int weight) {
        if (weight <= 0) return 0;
        return 100 + Math.max(0, weight - 1) * 20;
    }

    @Override
    public String getEstimatedDays() {
        return "約 1~2 個工作天送到指定地址";
    }
}

class StorePickupDelivery implements DeliveryMethod {
    @Override
    public String getMethodName() {
        return "7-11 / 全家 超商取貨";
    }

    @Override
    public int calculateFee(int weight) {
        if (weight <= 0) return 0;
        if (weight <= 5) {
            return 60;
        }
        return 60 + (weight - 5) * 30;
    }

    @Override
    public String getEstimatedDays() {
        return "約 2~3 個工作天送達指定門市";
    }
}

class InStoreSelfPickup implements DeliveryMethod {
    @Override
    public String getMethodName() {
        return "實體門市親自自取";
    }

    @Override
    public int calculateFee(int weight) {
        // 自取免運費
        return 0;
    }

    @Override
    public String getEstimatedDays() {
        return "可當日憑訂單編號至門市現取";
    }
}

class OrderService {
    private String orderId;
    private int packageWeight; 
    private DeliveryMethod deliveryMethod; // Composition

    public OrderService(String orderId, int packageWeight, DeliveryMethod deliveryMethod) {
        this.orderId = orderId;
        this.packageWeight = Math.max(0, packageWeight);
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getShippingFee() {
        if (deliveryMethod == null) return 0;
        return deliveryMethod.calculateFee(packageWeight);
    }

    public void printDeliveryDetails() {
        System.out.println("            訂 單 配 送 簽 單            ");
        System.out.println("訂單編號: " + orderId);
        System.out.println("包裹重量: " + packageWeight + " kg");
        if (deliveryMethod != null) {
            System.out.println("配送管道: " + deliveryMethod.getMethodName());
            System.out.println("預估時間: " + deliveryMethod.getEstimatedDays());
            System.out.println("計算運費: $" + getShippingFee() + " 元");
        } else {
            System.out.println("配送管道: 未選擇");
        }
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        System.out.println("測試 \n");

        OrderService order1 = new OrderService("ORD-8001", 3, new HomeDelivery());
        order1.printDeliveryDetails();

        OrderService order2 = new OrderService("ORD-8002", 2, new StorePickupDelivery());
        order2.printDeliveryDetails();

        OrderService order3 = new OrderService("ORD-8003", 5, new InStoreSelfPickup());
        order3.printDeliveryDetails();

        System.out.println("中途變更配送方式");
        System.out.println("【變更前】訂單 ORD-8001 採用宅配");
        
        order1.setDeliveryMethod(new InStoreSelfPickup());
        System.out.println("【變更後】訂單 ORD-8001 改為門市自取：");
        order1.printDeliveryDetails();
    }
}
