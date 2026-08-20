import java.util.Objects;
interface PricingPolicy {
    int finalPrice(int originalPrice);
}

class StandardPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

class VipPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 85 / 100;
    }
}

class ThresholdDiscountPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        int validPrice = Math.max(0, originalPrice);
        if (validPrice >= 2000) {
            return validPrice - 300;
        }
        return validPrice;
    }
}


interface NotificationChannel {
    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + " -> " + message);
        return true;
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.trim().length() < 8) {
            return false;
        }
        System.out.println("SMS " + receiver + " -> " + message);
        return true;
    }
}

class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final boolean notificationStatus;

    public CheckoutResult(String orderId, int originalPrice, int finalPrice, boolean notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public boolean isNotificationStatus() {
        return notificationStatus;
    }

    @Override
    public String toString() {
        return String.format("CheckoutResult { 訂單: %s  原價: $%d  折扣價: $%d  發送狀態: %s }",
                orderId, originalPrice, finalPrice, notificationStatus ? "成功" : "失敗");
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    public CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    public CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        if (orderId == null || orderId.isBlank() || originalPrice < 0) {
            return new CheckoutResult(orderId, originalPrice, 0, false);
        }

        int finalPrice = pricing.finalPrice(originalPrice);
        boolean status = channel.send(receiver, "order=" + orderId + ", amount=" + finalPrice);

        return new CheckoutResult(orderId, originalPrice, finalPrice, status);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        System.out.println("彈性結帳與通知系統測試\n");

        System.out.println(" StandardPricing + EmailChannel");
        CheckoutService service1 = new CheckoutService(new StandardPricing(), new EmailChannel());
        CheckoutResult res1 = service1.checkout("ORD-001", 1000, "user1@example.com");
        System.out.println("結果: " + res1 + "\n");

        System.out.println("StandardPricing + SmsChannel");
        CheckoutService service2 = new CheckoutService(new StandardPricing(), new SmsChannel());
        CheckoutResult res2 = service2.checkout("ORD-002", 1500, "0912345678");
        System.out.println("結果: " + res2 + "\n");

        System.out.println("VipPricing + EmailChannel");
        CheckoutService service3 = new CheckoutService(new VipPricing(), new EmailChannel());
        CheckoutResult res3 = service3.checkout("ORD-003", 2000, "vip@example.com");
        System.out.println("結果: " + res3 + "\n");

        System.out.println(" VipPricing + ConsoleChannel");
        CheckoutService service4 = new CheckoutService(new VipPricing(), new ConsoleChannel());
        CheckoutResult res4 = service4.checkout("ORD-004", 3000, "AdminConsole");
        System.out.println("結果: " + res4 + "\n");

        System.out.println("ThresholdDiscountPricing + SmsChannel");
        CheckoutService service5 = new CheckoutService(new ThresholdDiscountPricing(), new SmsChannel());
        CheckoutResult res5 = service5.checkout("ORD-005", 2500, "0987654321");
        System.out.println("結果: " + res5 + "\n");

        System.out.println("ThresholdDiscountPricing + ConsoleChannel");
        CheckoutService service6 = new CheckoutService(new ThresholdDiscountPricing(), new ConsoleChannel());
        CheckoutResult res6 = service6.checkout("ORD-006", 1800, "StoreTerminal");
        System.out.println("結果: " + res6 + "\n");

        System.out.println("Email格式錯誤");
        CheckoutResult res7 = service1.checkout("ORD-007", 500, "invalid-email-address");
        System.out.println("結果: " + res7);
    }
}
