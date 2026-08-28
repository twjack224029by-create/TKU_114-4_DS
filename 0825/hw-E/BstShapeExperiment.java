import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

class ExperimentBst {
    private TreeNode root;

    public ExperimentBst() {
        this.root = null;
    }

    public void insert(int val) {
        root = insertHelper(root, val);
    }

    private TreeNode insertHelper(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }
        if (val < node.val) {
            node.left = insertHelper(node.left, val);
        } else if (val > node.val) {
            node.right = insertHelper(node.right, val);
        }
        return node;
    }

    public int getHeight() {
        return getHeightHelper(root);
    }

    private int getHeightHelper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeightHelper(node.left), getHeightHelper(node.right));
    }

    public int getSearchComparisonCount(int target) {
        return searchHelper(root, target, 0);
    }

    private int searchHelper(TreeNode node, int target, int currentComparisons) {
        if (node == null) {
            return currentComparisons; 
        }

        currentComparisons++;

        if (target == node.val) {
            return currentComparisons;
        } else if (target < node.val) {
            return searchHelper(node.left, target, currentComparisons);
        } else {
            return searchHelper(node.right, target, currentComparisons);
        }
    }

    public int getTotalComparisonsForAllNodes(List<Integer> values) {
        int total = 0;
        for (int val : values) {
            total += getSearchComparisonCount(val);
        }
        return total;
    }
}

public class BstShapeExperiment {

    public static void main(String[] args) {
        System.out.println("BstShapeExperiment");

        List<Integer> baseValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        //Sorted Order

        List<Integer> sortedList = new ArrayList<>(baseValues);
        ExperimentBst bstSorted = new ExperimentBst();
        for (int v : sortedList) {
            bstSorted.insert(v);
        }

        //Reverse Sorted Order

        List<Integer> reverseList = new ArrayList<>(baseValues);
        Collections.reverse(reverseList);
        ExperimentBst bstReverse = new ExperimentBst();
        for (int v : reverseList) {
            bstReverse.insert(v);
        }

        // Balanced Order

        List<Integer> balancedList = Arrays.asList(8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15);
        ExperimentBst bstBalanced = new ExperimentBst();
        for (int v : balancedList) {
            bstBalanced.insert(v);
        }

        System.out.println("實驗順序說明");
        System.out.println("升冪排序順序 : " + sortedList);
        System.out.println("降冪逆序順序 : " + reverseList);
        System.out.println("隨機/平衡順序 : " + balancedList);
        System.out.println();

        System.out.println("結果比較");
        System.out.println("| 插入順序類型         | 樹高     | 全元素搜尋總比較次數   | 平均每次搜尋比較次數   |");

        printResultRow("升冪排序 (Sorted)  ", bstSorted, baseValues);
        printResultRow("降冪逆序 (Reverse) ", bstReverse, baseValues);
        printResultRow("隨機平衡 (Balanced)", bstBalanced, baseValues);

        System.out.println("結果分析");
        System.out.println("當資料以升冪降冪順序插入時，BST 會退化為右斜樹或左斜樹。");
        System.out.println("樹高達到最大值15，搜尋全體節點需比較120次 (平均8.00次)。");
        System.out.println("當資料以「平衡/交錯」順序插入時，樹結構發展均勻。");
        System.out.println("樹高大幅降至4，搜尋全體節點僅需比較49次 (平均 3.27 次)。");
        System.out.println("印證了BST的時間複雜度極度依賴樹的形狀：最壞情況 $O(N)$，最佳/平均情況為 $O(\\log N)$。");
    }

    private static void printResultRow(String label, ExperimentBst bst, List<Integer> values) {
        int height = bst.getHeight();
        int totalComp = bst.getTotalComparisonsForAllNodes(values);
        double avgComp = (double) totalComp / values.size();

        System.out.printf("| %-20s | %8d | %22d | %22.2f |%n",
                label, height, totalComp, avgComp);
    }
}

