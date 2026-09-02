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


}
