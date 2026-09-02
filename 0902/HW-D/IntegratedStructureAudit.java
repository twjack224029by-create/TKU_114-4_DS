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

    public static AuditReport auditScenario(String scenarioName, 
                                            DataStructure chosen, 
                                            boolean needRandomAccess, 
                                            boolean needOrdering, 
                                            boolean needPriority, 
                                            boolean needRelationship, 
                                            boolean HighFrequencySearch) {

        if (needRelationship) {
            if (chosen == DataStructure.GRAPH) {
                return new AuditReport(scenarioName, chosen, AuditStatus.OPTIMAL, "O(V + E)", "O(V + E)",
                        "圖 (Graph) 是唯一能自然表達頂點 (Vertex) 與邊 (Edge) 複雜多對多關係的結構。",
                        "維持使用 Adjacency List (鄰接串列) 儲存稀疏圖。");
            } else {
                return new AuditReport(scenarioName, chosen, AuditStatus.SUBOPTIMAL, "O(V^2) ~ O(2^V)", "O(V + E)",
                        "使用非 Graph 結構儲存網路關係會導致繁重的手動鏈結檢索，且無法直接運行 BFS/DFS 或 Dijkstra 演算法。",
                        "建議重構為 Graph (Adjacency List) 結構。");
            }
        }

        if (needPriority) {
            if (chosen == DataStructure.HEAP) {
                return new AuditReport(scenarioName, chosen, AuditStatus.OPTIMAL, "O(log N)", "O(log N)",
                        "Heap (PriorityQueue) 提供 O(1) 取最值與 O(log N) 插入/刪除，為優先級隊列最佳解。",
                        "維持使用 Heap / PriorityQueue。");
            } else if (chosen == DataStructure.BST) {
                return new AuditReport(scenarioName, chosen, AuditStatus.ACCEPTABLE, "O(log N)", "O(log N)",
                        "BST (如 TreeMap) 可維護排序並取得極值，但內部紅黑樹平衡開銷高於 Binary Heap，且空間佔用較大。",
                        "若僅需要取極值/優先級，建議改用 Heap (PriorityQueue)。");
            } else if (chosen == DataStructure.LIST) {
                return new AuditReport(scenarioName, chosen, AuditStatus.SUBOPTIMAL, "O(N) 或 O(N log N)", "O(log N)",
                        "每次尋找最高優先級皆需 O(N) 掃描，或每次插入皆需 O(N log N) 重新排序，造成嚴重效能瓶頸。",
                        "強烈建議改用 Heap (PriorityQueue)。");
            }
        }

        if (HighFrequencySearch && !needOrdering) {
            if (chosen == DataStructure.HASH_TABLE) {
                return new AuditReport(scenarioName, chosen, AuditStatus.OPTIMAL, "O(1)", "O(1)",
                        "Hash Table 提供平均 O(1) 的超高速 Key 查詢與插入，是無序鍵值查找的最佳工具。",
                        "維持使用 HashMap 或 HashSet。");
            } else if (chosen == DataStructure.BST) {
                return new AuditReport(scenarioName, chosen, AuditStatus.ACCEPTABLE, "O(log N)", "O(1)",
                        "BST (TreeSet/TreeMap) 可達到 O(log N) 搜尋，但不需要區間查詢或排序時，開銷大於 Hash Table。",
                        "若不需要元素有序，建議改用 Hash Table (HashMap/HashSet)。");
            } else if (chosen == DataStructure.LIST) {
                return new AuditReport(scenarioName, chosen, AuditStatus.SUBOPTIMAL, "O(N)", "O(1)",
                        "List 進行 contains() 或是 findById() 必須進行全列表線性掃描，複雜度為 O(N)。",
                        "強烈建議改用 Hash Table (HashMap/HashSet)。");
            }
        }

        if (needOrdering && !needPriority) {
            if (chosen == DataStructure.BST) {
                return new AuditReport(scenarioName, chosen, AuditStatus.OPTIMAL, "O(log N)", "O(log N)",
                        "平衡 BST (如 Red-Black Tree / TreeSet) 支援 O(log N) 搜尋，並高效支援範圍查詢 (subSet/rangeSearch)。",
                        "維持使用平衡 BST (TreeMap/TreeSet)。");
            } else if (chosen == DataStructure.HASH_TABLE) {
                return new AuditReport(scenarioName, chosen, AuditStatus.SUBOPTIMAL, "O(N log N)", "O(log N)",
                        "Hash Table 本質無序。若要進行範圍查詢，必須導出所有元素並進行排序，成本極高。",
                        "建議改用 BST (TreeMap / TreeSet)。");
            }
        }

        if (!needRandomAccess && !HighFrequencySearch && !needOrdering) {
            if (chosen == DataStructure.QUEUE) {
                return new AuditReport(scenarioName, chosen, AuditStatus.OPTIMAL, "O(1)", "O(1)",
                        "Queue (如 ArrayDeque) 專為 FIFO 設計，提供 O(1) 的入列 (offer) 與出列 (poll) 操作。",
                        "維持使用 Queue / ArrayDeque。");
            } else if (chosen == DataStructure.LIST) {
                return new AuditReport(scenarioName, chosen, AuditStatus.ACCEPTABLE, "O(N) [ArrayList.remove(0)]", "O(1)",
                        "若使用 ArrayList 作為 Queue，在頭部刪除元素 (remove(0)) 會引發 O(N) 的記憶體搬移。",
                        "若需頻繁佇列操作，建議改用 Queue (ArrayDeque / LinkedList)。");
            }
        }

        return new AuditReport(scenarioName, chosen, AuditStatus.OPTIMAL, "O(1) Access", "O(1) Access",
                "List (ArrayList) 適合連續空間儲存與依 Index (O(1)) 的隨機存取。",
                "維持使用 List (ArrayList)。");
    }

    public static void main(String[] args) {
        System.out.println("                   Audit System");

        List<AuditReport> reports = new ArrayList<>();

        reports.add(auditScenario("使用者 ID 登入驗證與查找 (用戶數 100 萬)", 
                DataStructure.LIST, false, false, false, false, true));

        reports.add(auditScenario("使用者 ID 登入驗證與查找 (最佳配置)", 
                DataStructure.HASH_TABLE, false, false, false, false, true));

        reports.add(auditScenario("醫院急診室病人看診順序 (高優先級先看)", 
                DataStructure.LIST, false, false, true, false, false));

        reports.add(auditScenario("醫院急診室病人看診順序 (最佳配置)", 
                DataStructure.HEAP, false, false, true, false, false));

        reports.add(auditScenario("電商商品價格範圍搜尋 ($100 - $500)", 
                DataStructure.HASH_TABLE, false, true, false, false, false));

        reports.add(auditScenario("捷運路線轉乘與最短路徑規劃", 
                DataStructure.LIST, false, false, false, true, false));

        reports.add(auditScenario("辦公室印表機文件列印佇列 (FIFO)", 
                DataStructure.QUEUE, false, false, false, false, false));

        for (AuditReport report : reports) {
            report.printResult();
        }

        System.out.println("                             診斷完成，系統運作正常");
    }
}
