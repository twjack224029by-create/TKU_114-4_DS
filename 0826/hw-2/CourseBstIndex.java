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

    public boolean removeCourse(String courseCode) {
        if (findCourse(courseCode) == null) {
            System.out.println("失敗,找不到課程代碼 [" + courseCode + "]");
            return false;
        }

        root = deleteHelper(root, courseCode);
        System.out.println("成功移除課程: 代碼 [" + courseCode + "]");
        return true;
    }

    private CourseNode deleteHelper(CourseNode node, String courseCode) {
        if (node == null) return null;

        int cmp = courseCode.compareTo(node.course.getCourseCode());
        if (cmp < 0) {
            node.left = deleteHelper(node.left, courseCode);
        } else if (cmp > 0) {
            node.right = deleteHelper(node.right, courseCode);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            CourseNode minNode = getMin(node.right);
            node.course = minNode.course;
            node.right = deleteHelper(node.right, minNode.course.getCourseCode());
        }
        return node;
    }
    
    private CourseNode getMin(CourseNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public boolean updateCredit(String courseCode, int newCredit) {
        if (newCredit < 1 || newCredit > 6) {
            System.out.println("失敗,學分必須在1-6(輸入值: " + newCredit + ")");
            return false;
        }

        Course course = findCourse(courseCode);
        if (course == null) {
            System.out.println("失敗,找不到課程代碼 [" + courseCode + "]");
            return false;
        }

        int oldCredit = course.getCredit();
        course.setCredit(newCredit);
        System.out.printf("成功更新課程 [%s] 學分: %d ➔ %d%n", courseCode, oldCredit, newCredit);
        return true;
    }

    public void printRangeQuery(String startCode, String endCode) {
        System.out.println("\n課程代碼範圍查詢 [" + startCode + " ~ " + endCode + "]");
        List<Course> result = new ArrayList<>();
        rangeSearchHelper(root, startCode, endCode, result);

        if (result.isEmpty()) {
            System.out.println("該範圍內無任何課程");
        } else {
            for (Course c : result) {
                System.out.println("  " + c);
            }
        }
    }

    private void rangeSearchHelper(CourseNode node, String startCode, String endCode, List<Course> list) {
        if (node == null) return;

        if (node.course.getCourseCode().compareTo(startCode) > 0) {
            rangeSearchHelper(node.left, startCode, endCode, list);
        }

        if (node.course.getCourseCode().compareTo(startCode) >= 0 && node.course.getCourseCode().compareTo(endCode) <= 0) {
            list.add(node.course);
        }

        if (node.course.getCourseCode().compareTo(endCode) < 0) {
            rangeSearchHelper(node.right, startCode, endCode, list);
        }
    }

    public void printSortedReport() {
        List<Course> list = new ArrayList<>();
        inOrderHelper(root, list);

        int totalCredits = 0;
        for (Course c : list) {
            totalCredits += c.getCredit();
        }

        System.out.println("\n課程系統排序總表 (Sorted Report)");
        System.out.println("總課程門數: " + list.size() + " 門");
        System.out.println("累計總學分: " + totalCredits + " 學分");
        if (list.isEmpty()) {
            System.out.println("目前系統無任何課程");
        } else {
            for (Course c : list) {
                System.out.println("  " + c);
            }
        }
    }

    private void inOrderHelper(CourseNode node, List<Course> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.course);
        inOrderHelper(node.right, list);
    }

    public static void main(String[] args) {
        System.out.println("CourseBstIndex\n");

        CourseBstIndex system = new CourseBstIndex();

        System.out.println("新增課程");
        system.addCourse("CS103", "Data Structures", 3);
        system.addCourse("CS101", "Introduction to CS", 2);
        system.addCourse("CS201", "Algorithms", 4);
        system.addCourse("CS102", "Object-Oriented Design", 3);
        system.addCourse("CS305", "Machine Learning", 3);

        System.out.println("\n測試邊界限制");
        system.addCourse("CS101", "Duplicate CS", 3); 
        system.addCourse("CS401", "Invalid Credit High", 8); 
        system.addCourse("CS402", "Invalid Credit Low", 0);  

        system.printSortedReport();

        System.out.println("查詢");
        Course found = system.findCourse("CS201");
        System.out.println("查詢 CS201 結果: " + (found != null ? found : "查無此課程"));

        Course notFound = system.findCourse("CS999");
        System.out.println("查詢 CS999 結果: " + (notFound != null ? notFound : "查無此課程"));

        System.out.println("\n更新學分");
        system.updateCredit("CS103", 4); 
        system.updateCredit("CS103", 7); 

        System.out.println("\n範圍查詢");
        system.printRangeQuery("CS102", "CS201");

        System.out.println("移除課程");
        system.removeCourse("CS101");

        system.printSortedReport();
    }
}
