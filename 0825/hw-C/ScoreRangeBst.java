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
