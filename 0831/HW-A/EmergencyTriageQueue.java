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
