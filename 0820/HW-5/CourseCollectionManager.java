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

    Map<String, Integer> scoreDistribution() {
        Map<String, Integer> dist = new LinkedHashMap<>();
        dist.put("A", 0);
        dist.put("B", 0);
        dist.put("C", 0);
        dist.put("D", 0);
        dist.put("F", 0);

        for (CourseEnrollment enrollment : order) {
            int score = enrollment.getScore();
            if (score >= 90) dist.put("A", dist.get("A") + 1);
            else if (score >= 80) dist.put("B", dist.get("B") + 1);
            else if (score >= 70) dist.put("C", dist.get("C") + 1);
            else if (score >= 60) dist.put("D", dist.get("D") + 1);
            else dist.put("F", dist.get("F") + 1);
        }
        return dist;
    }

    List<CourseEnrollment> ranking() {
        List<CourseEnrollment> result = new ArrayList<>(order);
        result.sort(Comparator.comparingInt(CourseEnrollment::getScore)
                .reversed()
                .thenComparing(CourseEnrollment::getStudentId));
        return result;
    }

    List<CourseEnrollment> top(int count) {
        List<CourseEnrollment> sorted = ranking();
        if (count <= 0) {
            return new ArrayList<>();
        }
        int limit = Math.min(count, sorted.size());
        return new ArrayList<>(sorted.subList(0, limit));
    }

    void removeBelow(int minimum) {
        order.removeIf(enrollment -> enrollment.getScore() < minimum);
        registeredIds.clear();
        byId.clear();
        for (CourseEnrollment enrollment : order) {
            registeredIds.add(enrollment.getStudentId());
            byId.put(enrollment.getStudentId(), enrollment);
        }
    }

    int size() {
        return order.size();
    }
}

public class CourseCollectionManager {
    public static void main(String[] args) {
        System.out.println("CourseCollectionManager test\n");

        RegistrationBook book = new RegistrationBook();

        CourseEnrollment s101 = new CourseEnrollment("S101", "Amy", 88);
        s101.addTag("Java");
        s101.addTag("  "); 

        CourseEnrollment s102 = new CourseEnrollment("S102", "Ben", 55);
        s102.addTag("Python");

        CourseEnrollment s103 = new CourseEnrollment("S103", "Cara", 92);
        s103.addTag("Java");
        s103.addTag("Tree");

        CourseEnrollment s104 = new CourseEnrollment("S104", "David", 92); // 與 S103 同分 (92)
        s104.addTag("Java");

        CourseEnrollment s105 = new CourseEnrollment("S105", "Emma", 45);
        s105.addTag("C++");

        CourseEnrollment s106 = new CourseEnrollment("S106", "Frank", 78);
        s106.addTag("");

         CourseEnrollment duplicateS101 = new CourseEnrollment("S101", "Amy_Dup", 100);

        System.out.println("報名test");
        System.out.println("S101 報名: " + book.enroll(s101));
        System.out.println("S101 重複學號報名 (預期失敗): " + book.enroll(duplicateS101));
        System.out.println("S102 報名: " + book.enroll(s102));
        System.out.println("S103 報名: " + book.enroll(s103));
        System.out.println("S104 報名: " + book.enroll(s104));
        System.out.println("S105 報名: " + book.enroll(s105));
        System.out.println("S106 報名: " + book.enroll(s106));
        System.out.println("目前成功報名總人數: " + book.size());

         System.out.println("\n 更新成績test");
        System.out.println("更新前 S102: " + book.find("S102"));
        book.updateScore("S102", 65);
        System.out.println("更新後 S102 (55 -> 65): " + book.find("S102"));

        System.out.println("\n 標籤搜尋test ");
        List<CourseEnrollment> javaStudents = book.findByTag("java");
        for (CourseEnrollment e : javaStudents) {
            System.out.println("- " + e);
        }

        System.out.println("成績等級統計");
        System.out.println("成績分佈統計: " + book.scoreDistribution());

        System.out.println("\n top tank test");
        System.out.println("前 3 名學生 (含同分):");
        for (CourseEnrollment e : book.top(3)) {
            System.out.println("- " + e);
        }

        System.out.println("\n 要求Top量大於總人數時:");
        System.out.println("回傳人數: " + book.top(10).size());

        System.out.println("\n 移除低於60分");
        System.out.println("清理前總人數: " + book.size());
        book.removeBelow(60);
        System.out.println("清理後總人數: " + book.size());

        System.out.println("清理後完整排名:");
        for (CourseEnrollment e : book.ranking()) {
            System.out.println("- " + e);
        }

        System.out.println("驗證包含檢測 - Map 搜尋 S105: " + book.find("S105"));
        System.out.println("驗證重新報名 - 被刪除的 S105 重新報名: " + book.enroll(new CourseEnrollment("S105", "Emma_New", 80)));
    }
}
