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
