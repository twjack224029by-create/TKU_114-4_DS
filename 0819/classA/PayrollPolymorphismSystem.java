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

class Taxi extends Transport {
    private static final int BASE_FARE = 85;
    private static final double BASE_DISTANCE = 1.2;
    private static final double STEP_DISTANCE = 0.5;
    private static final int STEP_FARE = 5;

    public Taxi(String routeName) {
        super(routeName);
    }

    @Override
    public int calculateFare(int distance) {
        if (distance <= 0) return 0;
        if (distance <= BASE_DISTANCE) {
            return BASE_FARE;
        }
        
        double extraDistance = distance - BASE_DISTANCE;
        int steps = (int) Math.ceil(extraDistance / STEP_DISTANCE);
        return BASE_FARE + (steps * STEP_FARE);
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        System.out.println("test \n");

        Transport[] transports = new Transport[] {
            new Bus("紅 23 號公車"),
            new Bus("307 路線公車"),
            new Taxi("大都會計程車 (T-001)"),
            new Taxi("台灣大車隊 (T-002)")
        };

        int testDistance = 15; 

        System.out.printf("模擬所有交通工具里程: %d 公里%n", testDistance);

        for (int i = 0; i < transports.length; i++) {
            Transport transport = transports[i];
            int fare = transport.calculateFare(testDistance); 

            System.out.printf("%d. [%s] -> 路線/車號: %-18s | 計算票價: $%d 元%n",
                    (i + 1), 
                    transport.getClass().getSimpleName(), 
                    transport.getRouteName(), 
                    fare);
        }


        System.out.println("\n不同里程測試");
        int[] distances = { 1, 5, 10, 25 };

        for (int dist : distances) {
            System.out.printf("\n--- 當里程為 %d 公里時 ---%n", dist);
            for (Transport t : transports) {
                System.out.printf("  %-20s 票價: $%d%n", t.getRouteName(), t.calculateFare(dist));
            }
        }
    }
}
