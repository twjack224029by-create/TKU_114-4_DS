import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
class Enrollment {
    private String studentId;
    private String courseCode;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(courseCode, that.courseCode);
    }

   @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

  @Override
    public String toString() {
        return String.format("Enrollment[學生ID=%s, 課程代碼=%s]", studentId, courseCode);
    }
}
