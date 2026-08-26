import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

class TreeNode {
    String val;
    TreeNode left;
    TreeNode right;

    public TreeNode(String val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class TraversalTestReport {

    public static List<String> inOrder(TreeNode root) {
        List<String> res = new ArrayList<>();
        inOrderHelper(root, res);
        return res;
    }

    private static void inOrderHelper(TreeNode node, List<String> res) {
        if (node == null) return;
        inOrderHelper(node.left, res);
        res.add(node.val);
        inOrderHelper(node.right, res);
    }

    public static List<String> preOrder(TreeNode root) {
        List<String> res = new ArrayList<>();
        preOrderHelper(root, res);
        return res;
    }

    private static void preOrderHelper(TreeNode node, List<String> res) {
        if (node == null) return;
        res.add(node.val);
        preOrderHelper(node.left, res);
        preOrderHelper(node.right, res);
    }

    public static List<String> postOrder(TreeNode root) {
        List<String> res = new ArrayList<>();
        postOrderHelper(root, res);
        return res;
    }

    private static void postOrderHelper(TreeNode node, List<String> res) {
        if (node == null) return;
        postOrderHelper(node.left, res);
        postOrderHelper(node.right, res);
        res.add(node.val);
    }

    public static List<String> levelOrder(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            res.add(current.val);

            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        return res;
    }

    public static void runTestCase(
            String testName,
            TreeNode root,
            List<String> expIn,
            List<String> expPre,
            List<String> expPost,
            List<String> expLevel) {

        System.out.println("測試情境: " + testName);

        verifyTraversal("In-order   ", inOrder(root), expIn);
        verifyTraversal("Pre-order  ", preOrder(root), expPre);
        verifyTraversal("Post-order ", postOrder(root), expPost);
        verifyTraversal("Level-order", levelOrder(root), expLevel);
        System.out.println();
    }

    private static void verifyTraversal(String type, List<String> actual, List<String> expected) {
        boolean match = actual.equals(expected);
        String status = match ? "PASS" : "FAIL";

        System.out.printf("  [%s] 預期: %-22s | 實際: %-22s | 結果: %s%n",
                type, expected.toString(), actual.toString(), status);
    }

    public static void main(String[] args) {
        System.out.println("TraversalTestReport\n");

        TreeNode emptyTree = null;
        runTestCase(
                "1. Empty Tree (空樹)",
                emptyTree,
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList()
        );

        TreeNode singleTree = new TreeNode("A");
        runTestCase(
                "2. Single-node Tree (單一節點樹)",
                singleTree,
                Arrays.asList("A"),
                Arrays.asList("A"),
                Arrays.asList("A"),
                Arrays.asList("A")
        );

        TreeNode onlyLeftTree = new TreeNode("A");
        onlyLeftTree.left = new TreeNode("B");
        onlyLeftTree.left.left = new TreeNode("C");
        runTestCase(
                "3. Only-left Tree (左斜樹)",
                onlyLeftTree,
                Arrays.asList("C", "B", "A"), 
                Arrays.asList("A", "B", "C"), 
                Arrays.asList("C", "B", "A"), 
                Arrays.asList("A", "B", "C") 
        );

        TreeNode onlyRightTree = new TreeNode("A");
        onlyRightTree.right = new TreeNode("B");
        onlyRightTree.right.right = new TreeNode("C");
        runTestCase(
                "4. Only-right Tree (右斜樹)",
                onlyRightTree,
                Arrays.asList("A", "B", "C"), 
                Arrays.asList("A", "B", "C"), 
                Arrays.asList("C", "B", "A"), 
                Arrays.asList("A", "B", "C")  
        );

        TreeNode completeTree = new TreeNode("A");
        completeTree.left = new TreeNode("B");
        completeTree.right = new TreeNode("C");
        completeTree.left.left = new TreeNode("D");
        completeTree.left.right = new TreeNode("E");
        completeTree.right.left = new TreeNode("F");
        completeTree.right.right = new TreeNode("G");
        runTestCase(
                "5. Complete Tree (完全二元樹)",
                completeTree,
                Arrays.asList("D", "B", "E", "A", "F", "C", "G"), 
                Arrays.asList("A", "B", "D", "E", "C", "F", "G"), 
                Arrays.asList("D", "E", "B", "F", "G", "C", "A"), 
                Arrays.asList("A", "B", "C", "D", "E", "F", "G")  
        );

        TreeNode irregularTree = new TreeNode("A");
        irregularTree.left = new TreeNode("B");
        irregularTree.right = new TreeNode("C");
        irregularTree.left.left = new TreeNode("D");
        irregularTree.left.left.left = new TreeNode("F");
        irregularTree.right.right = new TreeNode("E");
        runTestCase(
                "6. Irregular Tree (不規則樹)",
                irregularTree,
                Arrays.asList("F", "D", "B", "A", "C", "E"), 
                Arrays.asList("A", "B", "D", "F", "C", "E"), 
                Arrays.asList("F", "D", "B", "E", "C", "A"), 
                Arrays.asList("A", "B", "C", "D", "E", "F")  
        );
    }
}
