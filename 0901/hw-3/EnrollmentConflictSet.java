import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class EnrollmentRecord {
    private String studentId;
    private String courseId;
    private String timestamp;

    public EnrollmentRecord(String studentId, String courseId, String timestamp) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.timestamp = timestamp;
    }

    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("學號: %-8s  課號: %-8s  時間: %s", studentId, courseId, timestamp);
    }
}

class EnrollmentKey {
    private String studentId;
    private String courseId;

    public EnrollmentKey(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentKey that = (EnrollmentKey) o;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "(" + studentId + " - " + courseId + ")";
    }
}

public class EnrollmentConflictSet {
  private Set<EnrollmentKey> uniqueEnrollments;             
    private List<EnrollmentRecord> duplicateRecords;        
    private Map<String, Set<String>> studentCourseMap;        
    private Map<String, Set<String>> courseStudentMap;     

    public EnrollmentConflictSet() {
        this.uniqueEnrollments = new HashSet<>();
        this.duplicateRecords = new ArrayList<>();
        this.studentCourseMap = new HashMap<>();
        this.courseStudentMap = new HashMap<>();
    }

    public void processEnrollment(EnrollmentRecord record) {
        EnrollmentKey key = new EnrollmentKey(record.getStudentId(), record.getCourseId());

        if (!uniqueEnrollments.add(key)) {
            duplicateRecords.add(record);
            System.out.printf("重複選課衝突 學生 %s 重複選修 %s (時間: %s)%n",
                    record.getStudentId(), record.getCourseId(), record.getTimestamp());
        } else {
            studentCourseMap.computeIfAbsent(record.getStudentId(), k -> new HashSet<>())
                             .add(record.getCourseId());

            courseStudentMap.computeIfAbsent(record.getCourseId(), k -> new HashSet<>())
                             .add(record.getStudentId());

            System.out.printf("成功 學生 %s 成功選修 %s%n", record.getStudentId(), record.getCourseId());
        }
    }

    public void printReport() {
        System.out.println("                       選課系統重複性與統計分析報表");

        System.out.println("\n 發現之重複選課紀錄 (Duplicate Records):");
        if (duplicateRecords.isEmpty()) {
            System.out.println("   (無重複選課紀錄)");
        } else {
            for (int i = 0; i < duplicateRecords.size(); i++) {
                System.out.printf("   [%02d] %s%n", (i + 1), duplicateRecords.get(i));
            }
        }

        System.out.println("\n每人修課集合 (Student -> Selected Courses):");
        for (Map.Entry<String, Set<String>> entry : studentCourseMap.entrySet()) {
            System.out.printf("   • 學生 [%-8s]: %s (共 %d 門課)%n",
                    entry.getKey(), entry.getValue(), entry.getValue().size());
        }

        System.out.println("\n每門課修課人數與名單 (Course -> Enrolled Students):");
        for (Map.Entry<String, Set<String>> entry : courseStudentMap.entrySet()) {
            System.out.printf("   • 課程 [%-8s]: 修課人數 = %2d 人  學生清單: %s%n",
                    entry.getKey(), entry.getValue().size(), entry.getValue());
        }

        System.out.printf(" 數據統計摘要: 有效選課總人次 = %d  被攔截之重複紀錄 = %d%n",
                uniqueEnrollments.size(), duplicateRecords.size());
    }

    public static void main(String[] args) {
        EnrollmentConflictSet system = new EnrollmentConflictSet();

        System.out.println("開始數據輸入");

        List<EnrollmentRecord> rawRequests = List.of(
            new EnrollmentRecord("S101", "CS101", "9/1 09:00"),
            new EnrollmentRecord("S101", "CS102", "9/1 09:00"),
            new EnrollmentRecord("S102", "CS101", "9/1 09:01"),
            new EnrollmentRecord("S101", "CS101", "9/1 09:02"), 
            new EnrollmentRecord("S103", "CS103", "9/1 09:03"),
            new EnrollmentRecord("S102", "CS102", "9/1 09:04"),
            new EnrollmentRecord("S103", "CS101", "9/1 09:05"),
            new EnrollmentRecord("S102", "CS101", "9/1 09:06")  
        );

        for (EnrollmentRecord req : rawRequests) {
            system.processEnrollment(req);
        }

        system.printReport();
    }
}
