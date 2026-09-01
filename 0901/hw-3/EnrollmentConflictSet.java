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
  
}
