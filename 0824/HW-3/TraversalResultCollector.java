import java.util.ArrayDeque;
import java.util.ArrayList;
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

public class TraversalResultCollector {

    public static List<String> inOrder(TreeNode root) {
        List<String> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

    private static void inOrderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        inOrderHelper(node.left, result);
        result.add(node.val);
        inOrderHelper(node.right, result);
    }

    public static List<String> preOrder(TreeNode root) {
        List<String> result = new ArrayList<>();
        preOrderHelper(root, result);
        return result;
    }

    private static void preOrderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        result.add(node.val);
        preOrderHelper(node.left, result);
        preOrderHelper(node.right, result);
    }

    public static List<String> postOrder(TreeNode root) {
        List<String> result = new ArrayList<>();
        postOrderHelper(root, result);
        return result;
    }

    private static void postOrderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        postOrderHelper(node.left, result);
        postOrderHelper(node.right, result);
        result.add(node.val);
    }

    public static List<String> levelOrder(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.val);

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return result;
    }

    public static void runAndPrintAll(String treeDescription, TreeNode root) {
        System.out.println("樹結構測試: " + treeDescription);
        System.out.println("  In-order   : " + inOrder(root));
        System.out.println("  Pre-order  : " + preOrder(root));
        System.out.println("  Post-order : " + postOrder(root));
        System.out.println("  Level-order: " + levelOrder(root));
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("TraversalResultCollector \n");

        TreeNode emptyTree = null;
        runAndPrintAll("Empty Tree", emptyTree);

        TreeNode singleNodeTree = new TreeNode("A");
        runAndPrintAll("Single-node Tree", singleNodeTree);

        TreeNode leftSkewedTree = new TreeNode("A");
        leftSkewedTree.left = new TreeNode("B");
        leftSkewedTree.left.left = new TreeNode("C");
        runAndPrintAll("Left-skewed Tree", leftSkewedTree);

        TreeNode completeTree = new TreeNode("A");
        completeTree.left = new TreeNode("B");
        completeTree.right = new TreeNode("C");
        completeTree.left.left = new TreeNode("D");
        completeTree.left.right = new TreeNode("E");
        completeTree.right.left = new TreeNode("F");
        runAndPrintAll("Complete Tree", completeTree);
    }
}
