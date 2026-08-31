import java.util.Arrays;

class HashNode {
    int key;
    String value;
    HashNode next;

    public HashNode(int key, String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.format("%d: \"%s\"", key, value);
    }
}

public class IntegerStringHashTable {
  private HashNode[] buckets;     
    private int size;            
    private int capacity;           
    private static final double LOAD_FACTOR_THRESHOLD = 0.75; 

    public IntegerStringHashTable() {
        this(11);
    }

    public IntegerStringHashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new HashNode[capacity];
        this.size = 0;
    }

    private int hash(int key) {
        return Math.abs(key) % capacity;
    }

    public void put(int key, String value) {
        int bucketIndex = hash(key);
        HashNode head = buckets[bucketIndex];

        HashNode current = head;
        while (current != null) {
            if (current.key == key) {
                String oldValue = current.value;
                current.value = value;
                System.out.printf("更新Key: %d 的值由 \"%s\" 更新為 \"%s\" (Size 維持 %d)%n", 
                        key, oldValue, value, size);
                return;
            }
            current = current.next;
        }

        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        buckets[bucketIndex] = newNode;
        size++;
        System.out.printf("成功插入 Key: %d, Value: \"%s\" (當前 Size: %d)%n", key, value, size);

        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }
    public String get(int key) {
        int bucketIndex = hash(key);
        HashNode current = buckets[bucketIndex];

        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return null; 
    }
  

}
