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
