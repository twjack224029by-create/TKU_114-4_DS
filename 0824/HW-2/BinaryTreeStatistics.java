import java.util.NoSuchElementException;

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

public class BinaryTreeStatistics {

    public static int size(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    public static int sum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return root.val + sum(root.left) + sum(root.right);
    }

    public static int maximum(TreeNode root) {
        if (root == null) {
            throw new NoSuchElementException("錯誤,Empty Tree沒有最大值");
        }
        return findMaxHelper(root);
    }

    private static int findMaxHelper(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }

        int maxVal = root.val;
        int leftMax = findMaxHelper(root.left);
        int rightMax = findMaxHelper(root.right);

        if (leftMax > maxVal) {
            maxVal = leftMax;
        }
        if (rightMax > maxVal) {
            maxVal = rightMax;
        }

        return maxVal;
    }

    public static int leafCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return leafCount(root.left) + leafCount(root.right);
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public static boolean contains(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        if (root.val == target) {
            return true;
        }
        return contains(root.left, target) || contains(root.right, target);
    }

    public static void main(String[] args) {
        System.out.println("BinaryTreeStatistics");

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(-5);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(8);
        root.right.right = new TreeNode(15);
        root.right.right.right = new TreeNode(25);

        System.out.println("二元樹統計數據");
        System.out.println("  總節點數 (size)     : " + size(root));
        System.out.println("  節點總和 (sum)      : " + sum(root));
        System.out.println("  節點最大值 (maximum): " + maximum(root));
        System.out.println("  葉子節點數 (leaf)   : " + leafCount(root));
        System.out.println("  樹的高度 (height)   : " + height(root));

        System.out.println("\n 搜尋測試contains");
        System.out.println("  是否包含 8  : " + contains(root, 8));
        System.out.println("  是否包含 25 : " + contains(root, 25));
        System.out.println("  是否包含 99 : " + contains(root, 99));

        System.out.println("\n 測試全負數二元樹的maximum");
        TreeNode negRoot = new TreeNode(-10);
        negRoot.left = new TreeNode(-20);
        negRoot.right = new TreeNode(-5);
        System.out.println("  全負數樹最大值 (應為 -5): " + maximum(negRoot));

        System.out.println("\n 測試Empty Tree");
        TreeNode emptyRoot = null;
        System.out.println("  空樹 size     : " + size(emptyRoot));
        System.out.println("  空樹 sum      : " + sum(emptyRoot));
        System.out.println("  空樹 leafCount: " + leafCount(emptyRoot));
        System.out.println("  空樹 height   : " + height(emptyRoot));

        System.out.print("  空樹 maximum  : ");
        try {
            maximum(emptyRoot);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }
}
