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

class ComparisonBst {
    private TreeNode root;

    public ComparisonBst() {
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

    public int getTotalComparisonsForExistingKeys(List<Integer> keys) {
        int total = 0;
        for (int key : keys) {
            total += getSearchComparisonCount(key);
        }
        return total;
    }

    public int getTotalComparisonsForMissingKeys(List<Integer> missingKeys) {
        int total = 0;
        for (int key : missingKeys) {
            total += getSearchComparisonCount(key);
        }
        return total;
    }
}

public class TreeShapeComparison {

    public static void main(String[] args) {
        System.out.println("TreeShapeComparison");

        List<Integer> existingKeys = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        List<Integer> ascList = new ArrayList<>(existingKeys);
        ComparisonBst bstAsc = new ComparisonBst();
        for (int k : ascList) bstAsc.insert(k);

        List<Integer> descList = new ArrayList<>(existingKeys);
        Collections.reverse(descList);
        ComparisonBst bstDesc = new ComparisonBst();
        for (int k : descList) bstDesc.insert(k);

        List<Integer> balancedList = Arrays.asList(8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15);
        ComparisonBst bstBalanced = new ComparisonBst();
        for (int k : balancedList) bstBalanced.insert(k);

        System.out.println("升冪順序 (Ascending)     : " + ascList);
        System.out.println("降冪順序 (Descending)    : " + descList);
        System.out.println("接近平衡 (Near-Balanced) : " + balancedList);
        System.out.println();

        System.out.println("數據比較表");
        System.out.println("插入順序/樹形類型               樹高      存在 Key 總比較次數     存在 Key 平均比較次數   Missing Key(0) 比較次數");

        printRow("升冪順序 (Ascending)", bstAsc, existingKeys, 0);
        printRow("降冪順序 (Descending)", bstDesc, existingKeys, 0);
        printRow("接近平衡 (Balanced)", bstBalanced, existingKeys, 0);

        System.out.println("Missing Key搜尋比較");
        System.out.printf("搜尋Key = 0  (小於所有元素)  : 升冪樹 = %2d 次  降冪樹 = %2d 次  平衡樹 = %2d 次%n",
                bstAsc.getSearchComparisonCount(0), bstDesc.getSearchComparisonCount(0), bstBalanced.getSearchComparisonCount(0));
        System.out.printf("搜尋Key = 16 (大於所有元素)  : 升冪樹 = %2d 次  降冪樹 = %2d 次  平衡樹 = %2d 次%n",
                bstAsc.getSearchComparisonCount(16), bstDesc.getSearchComparisonCount(16), bstBalanced.getSearchComparisonCount(16));
        System.out.printf("搜尋Key = 20 (遠大於根節點)  : 升冪樹 = %2d 次  降冪樹 = %2d 次  平衡樹 = %2d 次%n",
                bstAsc.getSearchComparisonCount(20), bstDesc.getSearchComparisonCount(20), bstBalanced.getSearchComparisonCount(20));
  
    }

    private static void printRow(String label, ComparisonBst bst, List<Integer> keys, int sampleMissingKey) {
        int height = bst.getHeight();
        int totalExistComp = bst.getTotalComparisonsForExistingKeys(keys);
        double avgExistComp = (double) totalExistComp / keys.size();
        int missingComp = bst.getSearchComparisonCount(sampleMissingKey);

        System.out.printf(" %-20s  %8d  %22d  %22.2f  %22d %n",
                label, height, totalExistComp, avgExistComp, missingComp);
    }
}
