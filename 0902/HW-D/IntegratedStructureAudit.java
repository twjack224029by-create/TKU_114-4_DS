import java.util.ArrayList;
import java.util.List;

public class IntegratedStructureAudit {

    public enum DataStructure {
        LIST, QUEUE, BST, HEAP, HASH_TABLE, GRAPH
    }

    public enum AuditStatus {
        OPTIMAL,     
        ACCEPTABLE,  
        SUBOPTIMAL   
    }

    public static class AuditReport {
        private final String scenarioName;
        private final DataStructure chosenStructure;
        private final AuditStatus status;
        private final String actualComplexity;
        private final String optimalComplexity;
        private final String reasoning;
        private final String recommendation;

        public AuditReport(String scenarioName, DataStructure chosenStructure, AuditStatus status,
                           String actualComplexity, String optimalComplexity, 
                           String reasoning, String recommendation) {
            this.scenarioName = scenarioName;
            this.chosenStructure = chosenStructure;
            this.status = status;
            this.actualComplexity = actualComplexity;
            this.optimalComplexity = optimalComplexity;
            this.reasoning = reasoning;
            this.recommendation = recommendation;
        }

        public void printResult() {
            String statusBadge = switch (status) {
                case OPTIMAL -> "[最佳配置 (Optimal)]";
                case ACCEPTABLE -> " [尚可接受 (Acceptable)]";
                case SUBOPTIMAL -> "[不合理/效能瓶頸 (Suboptimal)]";
            };

            System.out.printf(" 測試情境: %s%n", scenarioName);
            System.out.printf("   採用結構: %-12s  評估狀態: %s%n", chosenStructure, statusBadge);
            System.out.printf("   當前複雜度: %-10s  最優時間複雜度: %s%n", actualComplexity, optimalComplexity);
            System.out.printf("   診斷分析: %s%n", reasoning);
            System.out.printf("   改進建議: %s%n", recommendation);
        }
    }



}
