class CourseGrade {
    private String studentId;
    private String name;
    private double regularScore;  
    private double midtermScore; 
    private double finalScore;    
    private double attendanceScore; 

    public CourseGrade(String studentId, String name, double regularScore, 
                       double midtermScore, double finalScore, double attendanceScore) {
        this.studentId = studentId;
        this.name = name;
        this.regularScore = validateScore(regularScore);
        this.midtermScore = validateScore(midtermScore);
        this.finalScore = validateScore(finalScore);
        this.attendanceScore = validateScore(attendanceScore);
    }

    private double validateScore(double score) {
        if (score < 0 || score > 100) {
            System.out.printf("輸入成績 %.1f 不符合規範 (0-100)，重置為 0 分%n", score);
            return 0.0;
        }
        return score;
    }

    public double calculateFinalScore() {
        return (regularScore * 0.50) 
             + (midtermScore * 0.20) 
             + (finalScore * 0.20) 
             + (attendanceScore * 0.10);
    }

    public String getLevel() {
        double total = calculateFinalScore();
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        if (total >= 60) return "D";
        return "F";
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("學號: %-8s  姓名: %-4s  平時: %5.1f  期中: %5.1f  期末: %5.1f  出席: %5.1f  總分: %5.2f  等級: %s",
                studentId, name, regularScore, midtermScore, finalScore, attendanceScore, calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] students = new CourseGrade[] {
            new CourseGrade("01", "迪迪", 85.0, 78.0, 92.0, 100.0),
            new CourseGrade("02", "糙匹", 95.0, 88.0, 90.0, 90.0),
            new CourseGrade("03", "阿強", 40.0, 50.0, 45.0, 60.0), 
            new CourseGrade("04", "阿傑", 70.0, 65.0, 80.0, 85.0),
            new CourseGrade("05", "白白", 30.0, 20.0, 40.0, 50.0)  
        };

        System.out.println("成績明細:");
        for (CourseGrade student : students) {
            System.out.println(student.toString());
        }

        double totalSum = 0;
        for (CourseGrade student : students) {
            totalSum += student.calculateFinalScore();
        }
        double classAverage = totalSum / students.length;
        System.out.printf("全班平均總分: %.2f 分%n", classAverage);

        CourseGrade topStudent = students[0];
        for (int i = 1; i < students.length; i++) {
            if (students[i].calculateFinalScore() > topStudent.calculateFinalScore()) {
                topStudent = students[i];
            }
        }
        System.out.printf("全班最高分學生: %s (%s)，總分: %.2f 分 (等級 %s)%n",
                topStudent.getName(), topStudent.getStudentId(), topStudent.calculateFinalScore(), topStudent.getLevel());

        System.out.println("\n 不及格");
        boolean hasFailedStudents = false;
        for (CourseGrade student : students) {
            if (student.calculateFinalScore() < 60.0) {
                System.out.printf("學號: %s  姓名: %s  總分: %.2f (等級: %s)%n",
                        student.getStudentId(), student.getName(), student.calculateFinalScore(), student.getLevel());
                hasFailedStudents = true;
            }
        }

        if (!hasFailedStudents) {
            System.out.println("無不及格學生。");
        }
    }
}
