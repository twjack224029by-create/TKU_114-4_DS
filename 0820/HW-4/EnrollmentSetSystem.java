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

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        System.out.println("報名系統test\n");

        Set<Enrollment> enrollmentSet = new HashSet<>();

        System.out.println("新增報名test");

        Enrollment e1 = new Enrollment("A001", "CS101");
        boolean res1 = enrollmentSet.add(e1);
        System.out.println("新增 " + e1 + " -> 結果: " + res1);

        Enrollment e2 = new Enrollment("A001", "MA102");
        boolean res2 = enrollmentSet.add(e2);
        System.out.println("新增 " + e2 + " -> 結果: " + res2);

        Enrollment e3 = new Enrollment("B002", "CS101");
        boolean res3 = enrollmentSet.add(e3);
        System.out.println("新增 " + e3 + " -> 結果: " + res3);

        System.out.println("\n重複報名test(同一課程)");
        Enrollment duplicateE1 = new Enrollment("A001", "CS101");
        boolean resDuplicate = enrollmentSet.add(duplicateE1);
        System.out.println("重複新增 " + duplicateE1 + " -> 結果: " + resDuplicate);

        System.out.println("\n contains() ");
        Enrollment targetSearch = new Enrollment("A001", "MA102");
        boolean hasEnrollment = enrollmentSet.contains(targetSearch);
        System.out.println("查詢是否存在 " + targetSearch + " -> 結果: " + hasEnrollment);

        System.out.println("\n test取消報名");
        Enrollment targetRemove = new Enrollment("A001", "CS101");
        boolean removeResult = enrollmentSet.remove(targetRemove);
        System.out.println("取消報名 " + targetRemove + " -> 結果: " + removeResult);

        boolean removeAgainResult = enrollmentSet.remove(targetRemove);
        System.out.println("再次取消報名 " + targetRemove + " -> 結果: " + removeAgainResult);

        System.out.println("報名清單 (共 " + enrollmentSet.size() + " 筆)");
        for (Enrollment e : enrollmentSet) {
            System.out.println("- " + e);
        }
    }
}
