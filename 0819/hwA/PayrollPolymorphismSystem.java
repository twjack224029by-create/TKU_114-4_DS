import java.util.Objects;

abstract class Transport {
    private final String routeName;

    public Transport(String routeName) {
        if (routeName == null || routeName.trim().isEmpty()) {
            throw new IllegalArgumentException("路線名稱不能為空");
        }
        this.routeName = routeName;
    }

    public String getRouteName() {
        return routeName;
    }
    public abstract int calculateFare(int distance);
}

class Bus extends Transport {
    private static final int BASE_FARE = 15;
    private static final int SECTION_DISTANCE = 8;

    public Bus(String routeName) {
        super(routeName);
    }

    @Override
    public int calculateFare(int distance) {
        if (distance <= 0) return 0;
        int sections = (int) Math.ceil((double) distance / SECTION_DISTANCE);
        return sections * BASE_FARE;
    }
}

