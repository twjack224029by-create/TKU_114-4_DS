import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {
  private int[] heap;  
    private int size;        
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayMinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayMinHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("初始容量必須大於 0");
        }
        this.heap = new int[initialCapacity];
        this.size = 0;
    }

    public void add(int value) {
        ensureCapacity(); 
        heap[size] = value;
        siftUp(size);
        size++;
        System.out.printf("新增 插入數值: %-4d  當前 Size: %-2d  容量: %d%n", value, size, heap.length);
    }

    public int remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap為空,無法執行,remove");
        }
        int minValue = heap[0];
        heap[0] = heap[size - 1];
        size--;

        if (size > 0) {
            siftDown(0);
        }
        return minValue;
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap為空,無法執行peek");
        }
        return heap[0];
    }

    public int[] snapshot() {
        return Arrays.copyOf(heap, size);
    }

    private void ensureCapacity() {
        if (size >= heap.length) {
            int newCapacity = heap.length * 2;
            heap = Arrays.copyOf(heap, newCapacity);
            System.out.printf("容量擴充,陣列空間已滿,自動擴充為兩倍容量 (%d ➔ %d)%n", 
                    heap.length / 2, newCapacity);
        }
    }

   private void siftUp(int index) {
        int target = heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (target >= heap[parentIndex]) {
                break;
            }
            heap[index] = heap[parentIndex];
            index = parentIndex;
        }
        heap[index] = target;
    }

    private void siftDown(int index) {
        int target = heap[index];
        int half = size / 2; 

        while (index < half) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            int smallest = leftChild;

            if (rightChild < size && heap[rightChild] < heap[leftChild]) {
                smallest = rightChild;
            }

            if (target <= heap[smallest]) {
                break;
            }

            heap[index] = heap[smallest];
            index = smallest;
        }
        heap[index] = target;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public int getCapacity() { return heap.length; }

  public static void main(String[] args) {
    ArrayMinHeap minHeap = new ArrayMinHeap(4);

        System.out.println("               ArrayMinHeap");

        int[] testData = {
            45, 12, 89, 7, 3, 99, 23, 1, 56, 34,
            -5, 78, 12, 67, 8, 90, 11, 2, 4, 100,
            -15, 50
        };

        System.out.println("開始連續插入 " + testData.length + " 筆資料");
        for (int val : testData) {
            minHeap.add(val);
        }

        System.out.println("插入完畢,當前Heap總數: " + minHeap.size() + "  當前底層陣列容量: " + minHeap.getCapacity());

        System.out.println("測試Peek");
        System.out.println("目前Heap頂點: " + minHeap.peek());

        System.out.println("\n測試Snapshot");
        System.out.println("Heap 陣列快照: " + Arrays.toString(minHeap.snapshot()));

        System.out.println("\n連續執行Remove");
        System.out.println("依序彈出之最小值 sequence:");
        int count = 0;
        while (!minHeap.isEmpty()) {
            count++;
            int min = minHeap.remove();
            System.out.printf("[%02d] 彈出: %-4d ", count, min);
            if (count % 5 == 0) System.out.println(); 
        }
        System.out.println("\n");

        System.out.println("測試空Heap");
        try {
            minHeap.remove();
        } catch (NoSuchElementException e) {
            System.out.println(" Successfully caught expected exception: " + e.getMessage());
        }
  }
}
