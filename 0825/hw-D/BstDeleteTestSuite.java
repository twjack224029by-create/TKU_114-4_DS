import java.util.ArrayList;
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

class BinarySearchTree {
    private TreeNode root;
    private int count;

    public BinarySearchTree() {
        this.root = null;
        this.count = 0;
    }

    public TreeNode getRoot() {
        return root;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean insert(int val) {
        int initialSize = count;
        root = insertHelper(root, val);
        return count > initialSize;
    }

    private TreeNode insertHelper(TreeNode node, int val) {
        if (node == null) {
            count++;
            return new TreeNode(val);
        }
        if (val < node.val) {
            node.left = insertHelper(node.left, val);
        } else if (val > node.val) {
            node.right = insertHelper(node.right, val);
        }
        return node;
    }

    public boolean delete(int val) {
        if (isEmpty()) {
            return false;
        }
        int initialSize = count;
        root = deleteHelper(root, val);
        return count < initialSize;
    }

    private TreeNode deleteHelper(TreeNode node, int val) {
        if (node == null) {
            return null;
        }

        if (val < node.val) {
            node.left = deleteHelper(node.left, val);
        } else if (val > node.val) {
            node.right = deleteHelper(node.right, val);
        } else {
            count--;

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            TreeNode successor = getMinNode(node.right);
            node.val = successor.val;
            count++; 
            node.right = deleteHelper(node.right, successor.val);
        }
        return node;
    }

    private TreeNode getMinNode(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> toInOrderList() {
        List<Integer> list = new ArrayList<>();
        inOrderHelper(root, list);
        return list;
    }

    private void inOrderHelper(TreeNode node, List<Integer> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.val);
        inOrderHelper(node.right, list);
    }
}

public class BstDeleteTestSuite {
    public class BstDeleteTestSuite {

    private static int testCount = 0;
    private static int passedCount = 0;

    public static void main(String[] args) {
        System.out.println("二元搜尋樹Delete完整單元測試套件");

        testEmptyTreeDelete();
        testMissingKeyDelete();
        testSingleRootDelete();
        testRootWithOneChildDelete();
        testRootWithTwoChildrenDelete();
        testSequentialDeleteToEmpty();

        System.out.printf(" 測試總結: 通過 %d / %d 個測試案例%n", passedCount, testCount);
    }


    private static void assertState(String testName, boolean success, List<Integer> expectedList, List<Integer> actualList) {
        testCount++;
        boolean listMatch = expectedList.equals(actualList);
        if (success && listMatch) {
            passedCount++;
            System.out.println("  [PASS] " + testName);
        } else {
            System.out.println("  [FAIL] " + testName);
            System.out.println("         期望序列: " + expectedList);
            System.out.println("         實際序列: " + actualList);
        }
    }

    private static void testEmptyTreeDelete() {
        System.out.println("Empty Tree Deletion");
        BinarySearchTree bst = new BinarySearchTree();

        boolean deleted = bst.delete(50);
        assertState("刪除空樹中的節點 50 應回傳 false", !deleted, List.of(), bst.toInOrderList());
        System.out.println();
    }

    private static void testMissingKeyDelete() {
        System.out.println("Missing Key Deletion");
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);

        boolean deleted = bst.delete(99);
        assertState("刪除不存在的 99 應失敗且不影響樹結構", !deleted, List.of(30, 50, 70), bst.toInOrderList());
        System.out.println();
    }

    private static void testSingleRootDelete() {
        System.out.println("Single Root Deletion");
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);

        boolean deleted = bst.delete(50);
        assertState("刪除唯一根節點 50 後樹應變為空", deleted && bst.isEmpty(), List.of(), bst.toInOrderList());
        System.out.println();
    }
        
    private static void testRootWithOneChildDelete() {
        System.out.println("Root with One Child");

        BinarySearchTree bstA = new BinarySearchTree();
        bstA.insert(50);
        bstA.insert(70);
        bstA.insert(80);
        bstA.delete(50);
        assertState("刪除僅有右子樹的 Root (50)", true, List.of(70, 80), bstA.toInOrderList());

        BinarySearchTree bstB = new BinarySearchTree();
        bstB.insert(50);
        bstB.insert(30);
        bstB.insert(20);
        bstB.delete(50);
        assertState("刪除僅有左子樹的 Root (50)", true, List.of(20, 30), bstB.toInOrderList());

        System.out.println();
    }
        
}
