import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private final String ticketId;
    private final String serviceType;
    private String status; // "WAITING", "COMPLETED", "CANCELLED"

    public ServiceTicket(String ticketId, String serviceType) {
        this.ticketId = ticketId;
        this.serviceType = serviceType;
        this.status = "WAITING";
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + ticketId + "] " + serviceType + " (" + status + ")";
    }
}

public class ServiceCenterWorkflow {
  private final Set<String> activeIds = new HashSet<>();
  private final Map<String, ServiceTicket> ticketMap = new HashMap<>();
  private final Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
  private final Deque<ServiceTicket> completedStack = new ArrayDeque<>();

  public boolean createTicket(String ticketId, String serviceType) {
        if (ticketId == null || ticketId.isBlank()) {
            System.out.println("失敗,票號不可為空");
            return false;
        }

        if (!activeIds.add(ticketId)) {
            System.out.println("失敗:,票號 [" + ticketId + "] 已存在，不得重複建立");
            return false;
        }

        ServiceTicket ticket = new ServiceTicket(ticketId, serviceType);
        ticketMap.put(ticketId, ticket);
        waitingQueue.addLast(ticket); 
        System.out.println("成功抽牌: " + ticket);
        printStatus();
        return true;
    }


  
}
