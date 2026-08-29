import java.util.ArrayList;
import java.util.List;

enum NodeType {
    FILE, DIRECTORY
}

class FileSystemNode {
    private String name;
    private NodeType type;
    private long size;
    private List<FileSystemNode> children;

    public FileSystemNode(String name) {
        this.name = name;
        this.type = NodeType.DIRECTORY;
        this.size = 0;
        this.children = new ArrayList<>();
    }

    public FileSystemNode(String name, long size) {
        this.name = name;
        this.type = NodeType.FILE;
        this.size = Math.max(0, size);
        this.children = new ArrayList<>();
    }

    public boolean addChild(FileSystemNode child) {
        if (this.type != NodeType.DIRECTORY) {
            System.out.println("錯誤,無法向檔案 [" + this.name + "] 新增子節點");
            return false;
        }
        this.children.add(child);
        return true;
    }

    public String getName() {
        return name;
    }

    public NodeType getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<FileSystemNode> getChildren() {
        return children;
    }
}

public class DirectoryTreeReport {
  public static long calculateDirectorySizesPostOrder(FileSystemNode node) {
        if (node == null) return 0;

        if (node.getType() == NodeType.FILE) {
            return node.getSize();
        }

        long currentDirectoryTotalSize = 0;
        for (FileSystemNode child : node.getChildren()) {
            currentDirectoryTotalSize += calculateDirectorySizesPostOrder(child);
        }

        node.setSize(currentDirectoryTotalSize);
        return currentDirectoryTotalSize;
    }

    public static int countTotalNodes(FileSystemNode node) {
        if (node == null) return 0;
        int count = 1;
        for (FileSystemNode child : node.getChildren()) {
            count += countTotalNodes(child);
        }
        return count;
    }

    public static int countFiles(FileSystemNode node) {
        if (node == null) return 0;
        if (node.getType() == NodeType.FILE) return 1;

        int count = 0;
        for (FileSystemNode child : node.getChildren()) {
            count += countFiles(child);
        }
        return count;
    }
}
