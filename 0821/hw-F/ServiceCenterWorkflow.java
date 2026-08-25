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

    public ServiceTicket processNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("失敗,目前沒有等待處理的票券");
            return null;
        }

        ServiceTicket ticket = waitingQueue.removeFirst();
        ticket.setStatus("COMPLETED");
        completedStack.push(ticket); 
        System.out.println("成功叫號服務: " + ticket);
        printStatus();
        return ticket;
    }

    public boolean cancelWaiting(String ticketId) {
        ServiceTicket ticket = ticketMap.get(ticketId);

        if (ticket == null) {
            System.out.println("失敗,找不到票號 [" + ticketId + "]");
            return false;
        }

        if (!"WAITING".equals(ticket.getStatus())) {
            System.out.println("失敗,票號 [" + ticketId + "] 目前狀態為 (" + ticket.getStatus() + ")，只能取消 WAITING 狀態的票券！");
            return false;
        }

        waitingQueue.remove(ticket);
        ticket.setStatus("CANCELLED");
        System.out.println("成功取消等待號碼: " + ticket);
        printStatus();
        return true;
    }

    public boolean undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("失敗,沒有已完成的服務紀錄可撤回");
            return false;
        }

        ServiceTicket ticket = completedStack.pop();
        ticket.setStatus("WAITING");
        waitingQueue.addFirst(ticket); 

        System.out.println("執行 Undo: 已將最後完成的票號 [" + ticket.getTicketId() + "] 插回等待佇列最前端");
        printStatus();
        return true;
    }

    public ServiceTicket findById(String ticketId) {
        ServiceTicket ticket = ticketMap.get(ticketId);
        if (ticket != null) {
            System.out.println("查詢結果 [" + ticketId + "]: " + ticket);
        } else {
            System.out.println("查詢結果 [" + ticketId + "]: 查無此票號資料");
        }
        return ticket;
    }



  
}
