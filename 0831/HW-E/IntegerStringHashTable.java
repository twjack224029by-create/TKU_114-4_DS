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

    public boolean containsKey(int key) {
        int bucketIndex = hash(key);
        HashNode current = buckets[bucketIndex];

        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean remove(int key) {
        int bucketIndex = hash(key);
        HashNode current = buckets[bucketIndex];
        HashNode prev = null;

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                System.out.printf("成功移除 Key: %d (當前 Size: %d)%n", key, size);
                return true;
            }
            prev = current;
            current = current.next;
        }

        System.out.printf("失敗,查無 Key: %d%n", key);
        return false;
    }

    public int size() {
        return size;
    }

    public void bucketReport() {
        System.out.println(String.format("                Hash Table Bucket報表 (容量: %d, 元素數: %d)", capacity, size));
        
        int activeBuckets = 0;
        int maxChainLength = 0;

        for (int i = 0; i < capacity; i++) {
            System.out.printf(" Bucket %02d ➔ ", i);
            HashNode current = buckets[i];
            if (current == null) {
                System.out.println("(Empty)");
            } else {
                activeBuckets++;
                int chainLength = 0;
                StringBuilder sb = new StringBuilder();
                while (current != null) {
                    sb.append(current.toString()).append(" ");
                    current = current.next;
                    chainLength++;
                }
                if (chainLength > maxChainLength) {
                    maxChainLength = chainLength;
                }
                System.out.println(sb.toString().trim() + " (長度: " + chainLength + ")");
            }
        }
        System.out.printf(" 統計摘要: 啟用中的 Bucket 數 = %d / %d  最大碰撞鏈長度 = %d%n", 
                activeBuckets, capacity, maxChainLength);
    }

    private void resize() {
        int oldCapacity = capacity;
        capacity = capacity * 2; 
        HashNode[] oldBuckets = buckets;
        buckets = new HashNode[capacity];
        size = 0; 

        System.out.printf("自動擴容 負載過高，將容量由 %d 擴充至 %d 並執行Rehashing%n", 
                oldCapacity, capacity);

        for (int i = 0; i < oldCapacity; i++) {
            HashNode current = oldBuckets[i];
            while (current != null) {
                put(current.key, current.value); 
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {
        IntegerStringHashTable table = new IntegerStringHashTable(5);

        System.out.println("Separate Chaining");
        table.put(10, "Apple");
        table.put(15, "Banana"); 
        table.put(20, "Cherry"); 
        table.put(3, "Dog");
        table.put(7, "Elephant");

        table.bucketReport();

        System.out.println("測試同Key更新機制");
        System.out.println("更新前 Size: " + table.size());
        table.put(15, "Blueberry"); 
        table.put(10, "Apricot");   
        System.out.println("更新後 Size: " + table.size() + " (確認 Size 未改變)\n");

        System.out.println("測試get與containsKey");
        System.out.println("get(15) -> " + table.get(15));
        System.out.println("get(20) -> " + table.get(20));
        System.out.println("get(99) -> " + table.get(99)); 
        System.out.println("containsKey(3) -> " + table.containsKey(3));
        System.out.println("containsKey(88) -> " + table.containsKey(88));

        System.out.println("\n自動擴容");
        table.put(42, "Fox");
        table.put(55, "Grape");
        table.put(88, "Horse"); 

        table.bucketReport();

        System.out.println("測試remove");
        table.remove(15); 
        table.remove(999); 

        System.out.println("\n最終報表");
        table.bucketReport();
        System.out.println("最終Hash Table總size: " + table.size());
    }
}
