import java.util.ArrayList;
import java.util.List;

class StudentScore {
    private final String studentId;
    private final String name;
    private final int score;

    public StudentScore(String studentId, String name, int score) {
        this.studentId = studentId;
        this.name = name;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(StudentScore other) {
        if (this.score != other.score) {
            return Integer.compare(this.score, other.score);
        }
        return this.studentId.compareTo(other.studentId);
    }

    @Override
    public String toString() {
        return String.format("[%s] %-8s - 分數: %3d 分", studentId, name, score);
    }
}

class ScoreNode {
    StudentScore data;
    ScoreNode left;
    ScoreNode right;

    public ScoreNode(StudentScore data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class ScoreRangeBst {

    private ScoreNode root;

    public ScoreRangeBst() {
        this.root = null;
    }

    public boolean insert(String studentId, String name, int score) {
        if (studentId == null || studentId.isBlank()) {
            System.out.println("失敗,無效的學號");
            return false;
        }

        StudentScore newStudent = new StudentScore(studentId, name, score);
        int initialSize = size();
        root = insertHelper(root, newStudent);

        if (size() > initialSize) {
            System.out.println("成功新增紀錄: " + newStudent);
            return true;
        } else {
            System.out.println("失敗,學生 [" + studentId + "] 紀錄已存在");
            return false;
        }
    }

    private ScoreNode insertHelper(ScoreNode node, StudentScore student) {
        if (node == null) {
            return new ScoreNode(student);
        }

        int cmp = student.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertHelper(node.left, student);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, student);
        }
        return node;
    }

    public List<StudentScore> findStudentsInScoreRange(int minScore, int maxScore) {
        List<StudentScore> result = new ArrayList<>();
        if (minScore > maxScore) {
            System.out.println("查詢無效:最小值 (" + minScore + ") 不可大於最大值 (" + maxScore + ")");
            return result;
        }

        rangeSearchHelper(root, minScore, maxScore, result);
        return result;
    }

    private void rangeSearchHelper(ScoreNode node, int minScore, int maxScore, List<StudentScore> result) {
        if (node == null) return;

        if (node.data.getScore() > minScore) {
            rangeSearchHelper(node.left, minScore, maxScore, result);
        }

        if (node.data.getScore() >= minScore && node.data.getScore() <= maxScore) {
            result.add(node.data);
        }

        if (node.data.getScore() < maxScore) {
            rangeSearchHelper(node.right, minScore, maxScore, result);
        }
    }

    public List<StudentScore> getAllSortedStudents() {
        List<StudentScore> list = new ArrayList<>();
        inOrderHelper(root, list);
        return list;
    }

    private void inOrderHelper(ScoreNode node, List<StudentScore> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.data);
        inOrderHelper(node.right, list);
    }

    public int size() {
        return sizeHelper(root);
    }

    private int sizeHelper(ScoreNode node) {
        if (node == null) return 0;
        return 1 + sizeHelper(node.left) + sizeHelper(node.right);
    }

    public void printRankReport() {
        List<StudentScore> sorted = getAllSortedStudents();
        System.out.println("\n學生成績排行榜");
        if (sorted.isEmpty()) {
            System.out.println("目前無任何成績紀錄");
        } else {
            for (int i = 0; i < sorted.size(); i++) {
                System.out.printf("  第 %2d 名 | %s%n", (i + 1), sorted.get(i));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("ScoreRangeBst \n");

        ScoreRangeBst tree = new ScoreRangeBst();

        System.out.println("匯入成績資料");
        tree.insert("S101", "Alice", 85);
        tree.insert("S102", "Bob", 92);
        tree.insert("S103", "Charlie", 78);
        tree.insert("S104", "David", 85); 
        tree.insert("S105", "Eve", 60);
        tree.insert("S106", "Frank", 100);
        tree.insert("S107", "Grace", 85); 

        tree.printRankReport();

        System.out.println("範圍查詢測試80分~95分");
        List<StudentScore> range1 = tree.findStudentsInScoreRange(80, 95);
        for (StudentScore s : range1) {
            System.out.println(s);
        }

        System.out.println("\n範圍查詢測試60分~80分");
        List<StudentScore> range2 = tree.findStudentsInScoreRange(60, 80);
        for (StudentScore s : range2) {
            System.out.println(s);
        }

        System.out.println("\n範圍查詢測試96分~99分");
        List<StudentScore> range3 = tree.findStudentsInScoreRange(96, 99);
        if (range3.isEmpty()) {
            System.out.println("該分數區間無學生");
        } else {
            for (StudentScore s : range3) {
                System.out.println(s);
            }
        }
    }
}
