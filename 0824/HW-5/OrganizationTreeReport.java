import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

class OrgNode {
    String name;       
    OrgNode left;      
    OrgNode right;     

    public OrgNode(String name) {
        this.name = name;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class OrganizationTreeReport {

    public static OrgNode findParent(OrgNode root, String targetName) {
        if (root == null || targetName == null || root.name.equalsIgnoreCase(targetName)) {
            return null; 
        }
        return findParentHelper(root, targetName);
    }

    private static OrgNode findParentHelper(OrgNode current, String targetName) {
        if (current == null) {
            return null;
        }

        if ((current.left != null && current.left.name.equalsIgnoreCase(targetName)) ||
            (current.right != null && current.right.name.equalsIgnoreCase(targetName))) {
            return current;
        }

        OrgNode leftResult = findParentHelper(current.left, targetName);
        if (leftResult != null) {
            return leftResult;
        }

        return findParentHelper(current.right, targetName);
    }

    public static int findDepth(OrgNode root, String targetName) {
        if (root == null || targetName == null) {
            return -1;
        }
        return findDepthHelper(root, targetName, 0);
    }

    private static int findDepthHelper(OrgNode current, String targetName, int currentDepth) {
        if (current == null) {
            return -1;
        }

        if (current.name.equalsIgnoreCase(targetName)) {
            return currentDepth;
        }

        int leftDepth = findDepthHelper(current.left, targetName, currentDepth + 1);
        if (leftDepth != -1) {
            return leftDepth;
        }

        return findDepthHelper(current.right, targetName, currentDepth + 1);
    }

    public static List<String> pathFromRoot(OrgNode root, String targetName) {
        List<String> path = new ArrayList<>();
        if (root == null || targetName == null) {
            return path;
        }

        if (!findPathHelper(root, targetName, path)) {
            path.clear(); 
        }
        return path;
    }

    private static boolean findPathHelper(OrgNode current, String targetName, List<String> path) {
        if (current == null) {
            return false;
        }

        path.add(current.name);

        if (current.name.equalsIgnoreCase(targetName)) {
            return true;
        }

        if (findPathHelper(current.left, targetName, path) || 
            findPathHelper(current.right, targetName, path)) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static void printByLevel(OrgNode root) {
        System.out.println("Level-by-Level");
        if (root == null) {
            System.out.println("空樹");
            return;
        }

        Queue<OrgNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + ": ");

            for (int i = 0; i < levelSize; i++) {
                OrgNode current = queue.poll();
                System.out.print("[" + current.name + "] ");

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        System.out.println("OrganizationTreeReport");

        OrgNode root = new OrgNode("CEO");
        root.left = new OrgNode("VP_Tech");
        root.right = new OrgNode("VP_Sales");

        root.left.left = new OrgNode("RD_Mgr");
        root.left.right = new OrgNode("IT_Mgr");

        root.right.right = new OrgNode("Sales_Mgr");

        printByLevel(root);

        System.out.println("\n findParent");
        OrgNode p1 = findParent(root, "RD_Mgr");
        System.out.println("RD_Mgr的直屬上級: " + (p1 != null ? p1.name : "無"));

        OrgNode pRoot = findParent(root, "CEO");
        System.out.println("CEO的直屬上級: " + (pRoot != null ? pRoot.name : "無 (Root)"));

        OrgNode pNotFound = findParent(root, "HR_Mgr");
        System.out.println("HR_Mgr (不存在) 的直屬上級: " + (pNotFound != null ? pNotFound.name : "無 (找不到單位)"));

        System.out.println("\n findDepth");
        System.out.println("CEO 的深度: " + findDepth(root, "CEO"));
        System.out.println("VP_Tech 的深度: " + findDepth(root, "VP_Tech"));
        System.out.println("IT_Mgr 的深度: " + findDepth(root, "IT_Mgr"));
        System.out.println("HR_Mgr (不存在) 的深度: " + findDepth(root, "HR_Mgr"));

        System.out.println("\n pathFromRoot");
        System.out.println("到 IT_Mgr 的管理路徑: " + pathFromRoot(root, "IT_Mgr"));
        System.out.println("到 Sales_Mgr 的管理路徑: " + pathFromRoot(root, "Sales_Mgr"));
        System.out.println("到 HR_Mgr (不存在) 的管理路徑: " + pathFromRoot(root, "HR_Mgr"));
    }
}
