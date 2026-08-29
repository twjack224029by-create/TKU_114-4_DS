import java.util.ArrayList;
import java.util.List;

class Course {
    private final String courseCode;
    private String courseName;
    private int credit;

    public Course(String courseCode, String courseName, int credit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        setCredit(credit);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public boolean setCredit(int credit) {
        if (credit < 1 || credit > 6) {
            return false;
        }
        this.credit = credit;
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s] 課程名稱: %-20s | 學分: %d", courseCode, courseName, credit);
    }
}

class CourseNode {
    Course course;
    CourseNode left;
    CourseNode right;

    public CourseNode(Course course) {
        this.course = course;
        this.left = null;
        this.right = null;
    }
}

public class CourseBstIndex {
  private CourseNode root;

    public CourseBstIndex() {
        this.root = null;
    }

    public boolean addCourse(String courseCode, String courseName, int credit) {
        if (courseCode == null || courseCode.isBlank()) {
            System.out.println("失敗,課程代碼不可為空");
            return false;
        }

        if (credit < 1 || credit > 6) {
            System.out.println("失敗,課程 [" + courseCode + "] 學分必須在1-6(輸入值: " + credit + ")");
            return false;
        }

        if (findCourse(courseCode) != null) {
            System.out.println("失敗,課程代碼 [" + courseCode + "] 已存在，不可重複加入");
            return false;
        }

        Course newCourse = new Course(courseCode, courseName, credit);
        root = insertHelper(root, newCourse);
        System.out.println("成功新增課程: " + newCourse);
        return true;
    }

    private CourseNode insertHelper(CourseNode node, Course course) {
        if (node == null) {
            return new CourseNode(course);
        }

        int cmp = course.getCourseCode().compareTo(node.course.getCourseCode());
        if (cmp < 0) {
            node.left = insertHelper(node.left, course);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, course);
        }
        return node;
    }

    public Course findCourse(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) return null;
        CourseNode node = searchHelper(root, courseCode);
        return (node != null) ? node.course : null;
    }

    private CourseNode searchHelper(CourseNode node, String courseCode) {
        if (node == null) return null;

        int cmp = courseCode.compareTo(node.course.getCourseCode());
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return searchHelper(node.left, courseCode);
        } else {
            return searchHelper(node.right, courseCode);
        }
    }

  
}
