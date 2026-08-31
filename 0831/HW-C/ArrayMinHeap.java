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

  
}
