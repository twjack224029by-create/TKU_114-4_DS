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
