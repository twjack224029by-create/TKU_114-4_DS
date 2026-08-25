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

    public void printSummary() {
        System.out.println("\n 當日運作摘要");
        System.out.println("總抽牌次數: " + ticketMap.size());
        System.out.println("等待中人數: " + waitingQueue.size());
        System.out.println("完成服務數: " + completedStack.size());

        long countWaiting = ticketMap.values().stream().filter(t -> "WAITING".equals(t.getStatus())).count();
        long countCompleted = ticketMap.values().stream().filter(t -> "COMPLETED".equals(t.getStatus())).count();
        long countCancelled = ticketMap.values().stream().filter(t -> "CANCELLED".equals(t.getStatus())).count();

        System.out.println("狀態分類統計: [WAITING: " + countWaiting + ", COMPLETED: " + countCompleted + ", CANCELLED: " + countCancelled + "]");
    }

    public void printStatus() {
        System.out.println("   [Waiting Queue]: " + waitingQueue);
        System.out.println("   [Completed Stack]: " + completedStack);
    }

    public static void main(String[] args) {
        System.out.println("ServiceCenterWorkflow test \n");
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        center.createTicket("A001", "開戶服務");
        center.createTicket("A002", "外匯申辦");
        center.createTicket("A003", "信用卡諮詢");

        System.out.println("\n[測試 1: 重複 ID 建立]");
        center.createTicket("A001", "重複的 A001");

        System.out.println("\n 叫號處理");
        center.processNext(); 
        center.processNext(); 

        System.out.println("\n 取消失敗情境");
        center.cancelWaiting("A999");
        center.cancelWaiting("A001"); 

        System.out.println("\n 取消等待中的號碼");
        center.cancelWaiting("A003"); 

        System.out.println("\n 補抽牌並處理");
        center.createTicket("A004", "貸款諮詢");
        center.createTicket("A005", "存摺補登");
        center.processNext(); 
        center.processNext(); 

        System.out.println("\n 連續兩次Undo");
        center.undoLastCompletion(); 
        center.undoLastCompletion(); 

        System.out.println("\n Undo後重新叫號驗證");
        center.processNext();
        center.processNext(); 

        System.out.println("\n 空Queue與Stack邊界");
        center.processNext(); 
        center.undoLastCompletion(); 
        center.undoLastCompletion(); 
        center.undoLastCompletion(); 
        center.undoLastCompletion(); 
        center.undoLastCompletion(); 

        center.printSummary();
    }
}
