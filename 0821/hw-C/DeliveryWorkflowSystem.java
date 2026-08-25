import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class DeliveryTask {
    private final String id;        
    private final String address;   
    private String status;      

    public DeliveryTask(String id, String address) {
        this.id = id;
        this.address = address;
        this.status = "WAITING";
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + id + "] -> " + address + " (" + status + ")";
    }
}

public class DeliveryWorkflowSystem {
    private final Map<String, DeliveryTask> taskMap = new HashMap<>();
    private final Queue<DeliveryTask> waitingQueue = new ArrayDeque<>();
    private final Deque<DeliveryTask> completedStack = new ArrayDeque<>();

    public boolean addDelivery(String id, String address) {
        if (id == null || id.isBlank()) {
            System.out.println("失敗,配送編號不可為空");
            return false;
        }

        if (taskMap.containsKey(id)) {
            System.out.println("失敗:,配送編號 [" + id + "] 已存在，不可重複加入");
            return false;
        }

        DeliveryTask task = new DeliveryTask(id, address);
        taskMap.put(id, task);
        waitingQueue.offer(task);
        System.out.println("-> 成功新增配送包裹: " + task);
        printStatus();
        return true;
    }

    public DeliveryTask processNextDelivery() {
        if (waitingQueue.isEmpty()) {
            System.out.println("失敗,現在沒有等待配送的包裹");
            return null;
        }

        DeliveryTask task = waitingQueue.poll();
        task.setStatus("DELIVERED");
        completedStack.push(task); 
        System.out.println("成功處理配送包裹: " + task);
        printStatus();
        return task;
    }

    public boolean undoLastDelivery() {
        if (completedStack.isEmpty()) {
            System.out.println("失敗,目前沒有已完成的配送紀錄可撤回");
            return false;
        }

        DeliveryTask task = completedStack.pop();
        task.setStatus("WAITING");
        
        ((Deque<DeliveryTask>) waitingQueue).addFirst(task);
        System.out.println("執行Undo：包裹 [" + task.getId() + "] 已撤回至等待配送佇列首位");
        printStatus();
        return true;
    }

    public DeliveryTask findById(String id) {
        DeliveryTask task = taskMap.get(id);
        if (task != null) {
            System.out.println("查詢結果 [" + id + "]: " + task);
        } else {
            System.out.println("查詢結果 [" + id + "]: 查無此包裹資料");
        }
        return task;
    }

    public void printStatistics() {
        System.out.println("\n 狀態統計");
        System.out.println("總包裹登記數: " + taskMap.size());
        System.out.println("等待配送人數: " + waitingQueue.size());
        System.out.println("已完成配送數: " + completedStack.size());
        
        long waitingCount = taskMap.values().stream().filter(t -> "WAITING".equals(t.getStatus())).count();
        long deliveredCount = taskMap.values().stream().filter(t -> "DELIVERED".equals(t.getStatus())).count();
        
        System.out.println("狀態分類計數: [WAITING: " + waitingCount + ", DELIVERED: " + deliveredCount + "]");
    }

    public void printStatus() {
        System.out.println("   [Queue 等待中]: " + waitingQueue);
        System.out.println("   [Stack 已完成]: " + completedStack);
    }
    
}




