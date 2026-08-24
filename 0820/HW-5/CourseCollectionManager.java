import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CourseEnrollment {
    private final String studentId;
    private final String name;
    private int score;
    private final Set<String> tags = new HashSet<>();

    CourseEnrollment(String studentId, String name, int score) {
        this.studentId = studentId;
        this.name = name;
        this.score = Math.max(0, Math.min(100, score));
    }

    String getStudentId() {
        return studentId;
    }

    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = Math.max(0, Math.min(100, score));
    }

    void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.add(tag.trim().toLowerCase());
        }
    }

    boolean hasTag(String tag) {
        return tag != null && tags.contains(tag.trim().toLowerCase());
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score + " tags=" + tags;
    }
}

class RegistrationBook {
    private final List<CourseEnrollment> order = new ArrayList<>();
    private final Set<String> registeredIds = new HashSet<>();
    private final Map<String, CourseEnrollment> byId = new HashMap<>();

    boolean enroll(CourseEnrollment enrollment) {
        if (enrollment == null || !registeredIds.add(enrollment.getStudentId())) {
            return false;
        }
        order.add(enrollment);
        byId.put(enrollment.getStudentId(), enrollment);
        return true;
    }

    CourseEnrollment find(String studentId) {
        return byId.get(studentId);
    }

    boolean updateScore(String studentId, int score) {
        CourseEnrollment student = find(studentId);
        if (student == null) {
            return false;
        }
        student.setScore(score);
        return true;
    }

    List<CourseEnrollment> findByTag(String tag) {
        List<CourseEnrollment> result = new ArrayList<>();
        if (tag == null || tag.isBlank()) {
            return result;
        }
        for (CourseEnrollment enrollment : order) {
            if (enrollment.hasTag(tag)) {
                result.add(enrollment);
            }
        }
        return result;
    }
