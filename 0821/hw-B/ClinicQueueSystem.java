import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Patient {
    private final String id;   
    private final String name; 

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}

public class ClinicQueueSystem {
    private final Queue<Patient> waitingQueue = new ArrayDeque<>();
    private final List<Patient> completedList = new ArrayList<>();

    public void register(String id, String name) {
        Patient patient = new Patient(id, name);
        waitingQueue.offer(patient);
        System.out.println("掛號成功: " + patient);
        printQueueStatus();
    }

    public boolean cancelRegistration(String id) {
        boolean removed = waitingQueue.removeIf(patient -> patient.getId().equalsIgnoreCase(id));
        if (removed) {
            System.out.println("成功取消病歷 [" + id + "] 的掛號");
        } else {
            System.out.println("失敗,候診隊列中未找到病歷號 [" + id + "]");
        }
        printQueueStatus();
        return removed;
    }

    public Patient callNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("失敗,目前沒有候診病患");
            return null;
        }

        Patient nextPatient = waitingQueue.poll();
        completedList.add(nextPatient);
        System.out.println("請叫號病患至診間看診: " + nextPatient);
        printQueueStatus();
        return nextPatient;
    }

    public Patient peekNext() {
        Patient next = waitingQueue.peek();
        if (next == null) {
            System.out.println("目前沒有下一位候診病患");
        } else {
            System.out.println("下一位預計看診病患: " + next);
        }
        return next;
    }

    public void printCompletedList() {
        System.out.println("\n 本日已完成看診清單 (共 " + completedList.size() + " 人)");
        if (completedList.isEmpty()) {
            System.out.println("(無紀錄)");
        } else {
            for (int i = 0; i < completedList.size(); i++) {
                System.out.println((i + 1) + ". " + completedList.get(i));
            }
        }
    }

    public void printQueueStatus() {
        System.out.println("目前候診隊列 (FIFO, 共 " + waitingQueue.size() + " 人): " + waitingQueue);
    }

    public static void main(String[] args) {
        System.out.println("掛號系統 test \n");
        ClinicQueueSystem clinic = new ClinicQueueSystem();

        clinic.register("P001", "陳小明");
        clinic.register("P002", "林美麗");
        clinic.register("P003", "張大衛");
        clinic.register("P004", "黃志強");

        System.out.println("\n[測試查看下一位]");
        clinic.peekNext();

        System.out.println("\n[測試叫號]");
        clinic.callNext();
        clinic.callNext();

        System.out.println("\n[測試取消指定病歷號]");
        clinic.cancelRegistration("P003");

        clinic.cancelRegistration("P999");

        System.out.println("\n[測試繼續叫號]");
        clinic.callNext(); 

        System.out.println("\n[測試隊列為空時叫號]");
        clinic.callNext();

        clinic.printCompletedList();
    }
    
}
