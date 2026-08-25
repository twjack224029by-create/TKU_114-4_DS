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
  
}




