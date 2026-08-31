import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

class Patient {
    private String medicalRecordId; 
    private String name;            
    private int triageLevel;       
    private long sequenceNumber;   

    public Patient(String medicalRecordId, String name, int triageLevel, long sequenceNumber) {
        if (triageLevel < 1 || triageLevel > 5) {
            throw new IllegalArgumentException("分類數必須為1-5");
        }
        this.medicalRecordId = medicalRecordId;
        this.name = name;
        this.triageLevel = triageLevel;
        this.sequenceNumber = sequenceNumber;
    }

    public String getMedicalRecordId() { return medicalRecordId; }
    public String getName() { return name; }
    public int getTriageLevel() { return triageLevel; }
    public long getSequenceNumber() { return sequenceNumber; }

    public String getTriageLevelName() {
        switch (triageLevel) {
            case 1: return "一級";
            case 2: return "二級";
            case 3: return "三級";
            case 4: return "四級";
            case 5: return "五級";
            default: return "未知";
        }
    }

    @Override
    public String toString() {
        return String.format("病歷號: %-8s  姓名: %-4s  檢傷: %s  報到序: #%d",
                medicalRecordId, name, getTriageLevelName(), sequenceNumber);
    }
}

public class EmergencyTriageQueue {
    private PriorityQueue<Patient> queue;
    private long globalSequenceCounter; 

    public EmergencyTriageQueue() {
        Comparator<Patient> stableComparator = (p1, p2) -> {
            if (p1.getTriageLevel() != p2.getTriageLevel()) {
                return Integer.compare(p1.getTriageLevel(), p2.getTriageLevel());
            }
            return Long.compare(p1.getSequenceNumber(), p2.getSequenceNumber());
        };

        this.queue = new PriorityQueue<>(stableComparator);
        this.globalSequenceCounter = 0;
    }

    public void registerPatient(String medicalRecordId, String name, int triageLevel) {
        globalSequenceCounter++;
        Patient patient = new Patient(medicalRecordId, name, triageLevel, globalSequenceCounter);
        queue.offer(patient);
        System.out.println("報到成功" + patient);
    }

    public Patient peekNextPatient() {
        if (queue.isEmpty()) {
            System.out.println("目前候診為空，無等待中病人。");
            return null;
        }
        Patient next = queue.peek();
        System.out.println("下一位" + next);
        return next;
    }

    public Patient callNextPatient() {
        if (queue.isEmpty()) {
            System.out.println("失敗,目前候診為空");
            return null;
        }
        Patient calledPatient = queue.poll();
        System.out.println("請病人" + calledPatient + " 進入");
        return calledPatient;
    }
    
    public int getWaitingCount() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triageQueue = new EmergencyTriageQueue();

        System.out.println("EmergencyTriageQueue");

        System.out.println("Empty Queue Handling");
        System.out.println("目前候診人數: " + triageQueue.getWaitingCount() + " 人");
        triageQueue.peekNextPatient(); 
        triageQueue.callNextPatient();
        System.out.println();

        System.out.println("Registering Patients");
        triageQueue.registerPatient("P101", "張三", 3); 
        triageQueue.registerPatient("P102", "李四", 1); 
        triageQueue.registerPatient("P103", "王五", 2); 
        triageQueue.registerPatient("P104", "趙六", 3); 
        triageQueue.registerPatient("P105", "孫七", 1); 
        
        System.out.println("\n目前總候診人數: " + triageQueue.getWaitingCount() + " 人\n");

        System.out.println("查看下一位病人");
        triageQueue.peekNextPatient(); 
        System.out.println();

        System.out.println("Calling Patients");
        while (!triageQueue.isEmpty()) {
            System.out.println("剩餘人數: " + triageQueue.getWaitingCount() + " 人");
            triageQueue.callNextPatient();
        }

        System.out.println("\n所有病人看完診後，測試空佇列");
        System.out.println("目前候診人數: " + triageQueue.getWaitingCount() + " 人");
        triageQueue.callNextPatient(); 

    }
}
