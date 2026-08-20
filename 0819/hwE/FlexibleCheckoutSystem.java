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


