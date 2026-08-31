import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

class Event {
    private int eventId;        
    private double time;    
    private String type;    
    private int sequence;       
    private String description; 

    public Event(int eventId, double time, String type, int sequence, String description) {
        this.eventId = eventId;
        this.time = time;
        this.type = type;
        this.sequence = sequence;
        this.description = description;
    }

    public int getEventId() { return eventId; }
    public double getTime() { return time; }
    public String getType() { return type; }
    public int getSequence() { return sequence; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("ID: %-3d  時間: %6.2f  序號: %-2d  類型: %-12s  內容: %s",
                eventId, time, sequence, type, description);
    }
}

public class EventSimulationQueue {

    private PriorityQueue<Event> eventQueue;
    private Set<Integer> cancelledEventIds; 
    private List<String> executionLog;      
    private double currentTime;            

    public EventSimulationQueue() {
        Comparator<Event> eventComparator = (e1, e2) -> {
            if (Double.compare(e1.getTime(), e2.getTime()) != 0) {
                return Double.compare(e1.getTime(), e2.getTime());
            }
            return Integer.compare(e1.getSequence(), e2.getSequence());
        };

        this.eventQueue = new PriorityQueue<>(eventComparator);
        this.cancelledEventIds = new HashSet<>();
        this.executionLog = new ArrayList<>();
        this.currentTime = 0.0;
    }

    public void scheduleEvent(int eventId, double time, String type, int sequence, String description) {
        if (time < currentTime) {
            System.out.println("無法排程時間小於當前模擬時間 (" + currentTime + ") 的過去事件 (ID: " + eventId + ")");
            return;
        }
        Event newEvent = new Event(eventId, time, type, sequence, description);
        eventQueue.offer(newEvent);
        System.out.println("已排程 " + newEvent);
    }

    public boolean cancelEvent(int eventId) {
        if (cancelledEventIds.contains(eventId)) {
            System.out.println("失敗,事件 ID #" + eventId + " 此前已經被取消。");
            return false;
        }

        boolean exists = false;
        for (Event e : eventQueue) {
            if (e.getEventId() == eventId) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("失敗,查無事件 ID #" + eventId + " (可能已執行或不存在)");
            return false;
        }

        cancelledEventIds.add(eventId);
        System.out.println("已標記取消事件 ID #" + eventId);
        return true;
    }

    public void runSimulation() {
        System.out.println("                         開始模擬");
        int executedCount = 0;
        int skippedCount = 0;

        while (!eventQueue.isEmpty()) {
            Event currentEvent = eventQueue.poll();

            if (cancelledEventIds.contains(currentEvent.getEventId())) {
                String skipMessage = String.format(" 跳過/已取消 時間: %6.2f  事件 ID: #%-3d 類型: %s",
                        currentEvent.getTime(), currentEvent.getEventId(), currentEvent.getType());
                executionLog.add(skipMessage);
                skippedCount++;
                continue;
            }

            this.currentTime = currentEvent.getTime();

            String logMessage = String.format(" 成功執行 時間: %6.2f  " + currentEvent, currentTime);
            executionLog.add(logMessage);
            System.out.println(logMessage);
            executedCount++;
        }

        System.out.println(String.format("共執行 %d 個事件，跳過 %d 個已取消事件。", executedCount, skippedCount));
    }

    public void printExecutionLog() {
        System.out.println("                         完整執行紀錄");
        if (executionLog.isEmpty()) {
            System.out.println("目前無任何執行紀錄");
        } else {
            for (int i = 0; i < executionLog.size(); i++) {
                System.out.printf(" Step %02d: %s%n", (i + 1), executionLog.get(i));
            }
        }
    }

    public static void main(String[] args) {
        EventSimulationQueue simulator = new EventSimulationQueue();

        System.out.println("排程模擬事件");
        simulator.scheduleEvent(101, 10.5, "SYS INIT", 1, "初始化");
        simulator.scheduleEvent(102, 15.0, "USER LOGIN", 2, "Alice登入");
        simulator.scheduleEvent(103, 15.0, "USER LOGIN", 1, "Bob登入");
        simulator.scheduleEvent(104, 20.0, "DATA BACKUP", 1, "例行性資料備份");
        simulator.scheduleEvent(105, 12.0, "CHECK STATUS", 1, "健康檢查機制");
        simulator.scheduleEvent(106, 20.0, "TIMEOUT CHECK", 2, "連線逾時檢查");

        System.out.println("\nCancel Events");
        simulator.cancelEvent(104);
        simulator.cancelEvent(999);

        System.out.println("\n插入事件");
        simulator.scheduleEvent(107, 18.2, "ALERT_NOTIFY", 1, "發送系統警告通知");

        simulator.runSimulation();
        simulator.printExecutionLog();
    }
}
