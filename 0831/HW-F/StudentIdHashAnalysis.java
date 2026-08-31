import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentIdHashAnalysis {

    public static class AnalysisResult {
        public int bucketCount;          
        public int totalStudents;         
        public int[] bucketSizes;         
        public int totalCollisions;     
        public int maxChainLength;       
        public double avgChainLength;  
        public int nonZeroBuckets;       

        public AnalysisResult(int bucketCount, int totalStudents) {
            this.bucketCount = bucketCount;
            this.totalStudents = totalStudents;
            this.bucketSizes = new int[bucketCount];
        }

        public void printReport() {
            System.out.printf("                   學號 Hash Collision 分析報表 (Bucket 數: %d)%n", bucketCount);
            System.out.printf(" 總學號筆數 (Total Items): %d%n", totalStudents);
            System.out.printf(" 總碰撞次數 (Total Collisions): %d%n", totalCollisions);
            System.out.printf(" 最大Chain長度 (Max Chain Length): %d%n", maxChainLength);
            System.out.printf(" 平均非空Chain長度 (Avg Non-Empty Chain): %.2f%n", avgChainLength);
            System.out.printf(" 使用率 (Bucket Utilization): %d / %d (%.2f%%)%n", 
                    nonZeroBuckets, bucketCount, (nonZeroBuckets * 100.0 / bucketCount));
            System.out.println(" 各 Bucket 分佈明細:");
            for (int i = 0; i < bucketCount; i++) {
                int size = bucketSizes[i];
                StringBuilder bar = new StringBuilder();
                for (int j = 0; j < size; j++) bar.append("*");
                System.out.printf("   Bucket [%02d]: %2d 筆 %s%n", i, size, bar.toString());
            }
        }
    }

    public static AnalysisResult analyze(List<String> studentIds, int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket 數量必須大於 0");
        }

        AnalysisResult result = new AnalysisResult(bucketCount, studentIds.size());

        for (String id : studentIds) {
            int hashIndex = Math.abs(id.hashCode()) % bucketCount;
            result.bucketSizes[hashIndex]++;
        }

        int totalCollisions = 0;
        int maxChain = 0;
        int nonZeroBuckets = 0;
        int sumNonZeroLengths = 0;

        for (int size : result.bucketSizes) {
            if (size > 0) {
                nonZeroBuckets++;
                sumNonZeroLengths += size;
                if (size > 1) {
                    totalCollisions += (size - 1);
                }
                if (size > maxChain) {
                    maxChain = size;
                }
            }
        }

        result.totalCollisions = totalCollisions;
        result.maxChainLength = maxChain;
        result.nonZeroBuckets = nonZeroBuckets;
        result.avgChainLength = (nonZeroBuckets == 0) ? 0.0 : (double) sumNonZeroLengths / nonZeroBuckets;

        return result;
    }
  
    public static void compareBucketCounts(List<String> studentIds, int bucketCountA, int bucketCountB) {
        AnalysisResult resA = analyze(studentIds, bucketCountA);
        AnalysisResult resB = analyze(studentIds, bucketCountB);

        resA.printReport();
        resB.printReport();

        System.out.println("                       綜合對比");
        System.out.printf(" 評估指標                     Bucket Count A (%d)   Bucket Count B (%d)%n", 
                bucketCountA, bucketCountB);
        System.out.printf(" 總學號筆數                   %-20d  %-20d%n", studentIds.size(), studentIds.size());
        System.out.printf(" 總碰撞次數 (Collisions)      %-20d  %-20d%n", resA.totalCollisions, resB.totalCollisions);
        System.out.printf(" 最大 Chain 長度              %-20d  %-20d%n", resA.maxChainLength, resB.maxChainLength);
        System.out.printf(" 平均非空 Chain 長度          %-20.2f  %-20.2f%n", resA.avgChainLength, resB.avgChainLength);
        System.out.printf(" Bucket 使用率                %-19.2f%%  %-19.2f%%%n", 
                (resA.nonZeroBuckets * 100.0 / resA.bucketCount),
                (resB.nonZeroBuckets * 100.0 / resB.bucketCount));
    }

    public static void main(String[] args) {
        List<String> studentIds = Arrays.asList(
            "S110001", "S110002", "S110003", "S110012", "S110015",
            "S110022", "S110033", "S110045", "S110050", "S110061",
            "S110077", "S110088", "S110099", "S110100", "S110105"
        );

        System.out.println("Hash Collision分析");
        System.out.println("測試學號清單: " + studentIds + "\n");

        int bucketCountA = 5;
        int bucketCountB = 11;

        compareBucketCounts(studentIds, bucketCountA, bucketCountB);
    }
}
