import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ServiceRequestSystem {

    public enum RequestStatus {
        PENDING,  
        PROCESSED,
        CANCELLED 
    }

    public static class ServiceRequest {
        private final String id;
        private final String description;
        private final int priority;     
        private final long sequence;   
        private RequestStatus status;

        public ServiceRequest(String id, String description, int priority, long sequence) {
            this.id = id;
            this.description = description;
            this.priority = priority;
            this.sequence = sequence;
            this.status = RequestStatus.PENDING;
        }

        public String getId() { return id; }
        public String getDescription() { return description; }
        public int getPriority() { return priority; }
        public long getSequence() { return sequence; }
        public RequestStatus getStatus() { return status; }
        public void setStatus(RequestStatus status) { this.status = status; }

        @Override
        public String toString() {
            return String.format("Request[ID=%s, Priority=%d, Desc='%s', Status=%s]",
                    id, priority, description, status);
        }
    }

    private final Map<String, ServiceRequest> requestMap;
    private final PriorityQueue<ServiceRequest> priorityQueue;
    
    private long globalSequenceCounter = 0; 

    public ServiceRequestSystem() {
        this.requestMap = new HashMap<>();

        Comparator<ServiceRequest> comparator = (r1, r2) -> {
            if (r2.getPriority() != r1.getPriority()) {
                return Integer.compare(r2.getPriority(), r1.getPriority()); 
            }
            return Long.compare(r1.getSequence(), r2.getSequence()); 
        };

        this.priorityQueue = new PriorityQueue<>(comparator);
    }

    public boolean submitRequest(String id, String description, int priority) {
        if (id == null || id.trim().isEmpty() || requestMap.containsKey(id)) {
            System.out.printf("新增失敗, 請求 ID '%s' 已存在或無效%n", id);
            return false;
        }

        ServiceRequest request = new ServiceRequest(id, description, priority, ++globalSequenceCounter);
        requestMap.put(id, request);
        priorityQueue.offer(request);
        System.out.printf("新增 %s%n", request);
        return true;
    }

    public ServiceRequest getRequestById(String id) {
        return requestMap.get(id);
    }

    public ServiceRequest processNextRequest() {
        while (!priorityQueue.isEmpty()) {
            ServiceRequest candidate = priorityQueue.poll();
            
            ServiceRequest realRequest = requestMap.get(candidate.getId());

            if (realRequest == null || realRequest.getStatus() != RequestStatus.PENDING) {
                continue;
            }
            
            realRequest.setStatus(RequestStatus.PROCESSED);
            System.out.printf("處理請求 %s%n", realRequest);
            return realRequest;
        }

        System.out.println("佇列為空 目前沒有待處理的服務請求。");
        return null;
    }

    public boolean cancelRequest(String id) {
        ServiceRequest request = requestMap.get(id);

        if (request == null) {
            System.out.printf("失敗,找不到 ID 為 '%s' 的請求。%n", id);
            return false;
        }

        if (request.getStatus() != RequestStatus.PENDING) {
            System.out.printf("失敗,請求 '%s' 狀態為 %s，無法取消%n", id, request.getStatus());
            return false;
        }

        request.setStatus(RequestStatus.CANCELLED);
        System.out.printf("請求 '%s' 已設為已取消 (CANCELLED)。%n", id);
        return true;
    }

    public void printReport() {
        System.out.println("                        服務管理系統");
        System.out.printf(" HashMap 總紀錄數: %d  PriorityQueue 殘留節點數: %d%n", requestMap.size(), priorityQueue.size());
        for (ServiceRequest req : requestMap.values()) {
            System.out.println(" " + req);
        }
    }

    public static void main(String[] args) {
        ServiceRequestSystem system = new ServiceRequestSystem();

        System.out.println("送出多筆不同優先級請求");
        system.submitRequest("REQ-01", "一般用戶密碼重設", 1);
        system.submitRequest("REQ-02", "VIP客戶交易異常", 5);
        system.submitRequest("REQ-03", "伺服器 CPU 負載過高", 5);
        system.submitRequest("REQ-04", "網頁版面微調需求", 2);

        system.printReport();

        System.out.println("測試取消請求與雙重結構");
        system.cancelRequest("REQ-02");
        system.cancelRequest("REQ-02");

        System.out.println("\n測試依優先級處理");
        system.processNextRequest(); 
        
        system.processNextRequest();

        System.out.println("\n測試ID查詢");
        System.out.println("查詢 REQ-01 狀態: " + system.getRequestById("REQ-01"));
        System.out.println("查詢 REQ-02 狀態: " + system.getRequestById("REQ-02"));

        System.out.println("\n處理剩餘請求與邊界測試");
        system.processNextRequest(); 
        system.processNextRequest(); 

        system.printReport();
    }
}
