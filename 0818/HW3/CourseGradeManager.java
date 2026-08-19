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
