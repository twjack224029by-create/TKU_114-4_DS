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

    
}








