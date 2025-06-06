import java.util.HashMap;
import java.util.Map;

public class LRUCacheConsole {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
    }

    private void addNode(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCacheConsole(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        tail = new DLinkedNode();

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) return -1;
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);

        if (node == null) {
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            cache.put(key, newNode);
            addNode(newNode);
            size++;

            if (size > capacity) {
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                size--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing LRU Cache Implementation");
        
        LRUCacheConsole cache = new LRUCacheConsole(2);
        
        cache.put(1, 1);
        System.out.println("put(1, 1)");
        
        cache.put(2, 2);
        System.out.println("put(2, 2)");
        
        System.out.println("get(1): " + cache.get(1));  // returns 1
        
        cache.put(3, 3);
        System.out.println("put(3, 3) - evicts key 2");
        
        System.out.println("get(2): " + cache.get(2));  // returns -1
        
        cache.put(4, 4);
        System.out.println("put(4, 4) - evicts key 1");
        
        System.out.println("get(1): " + cache.get(1));  // returns -1
        System.out.println("get(3): " + cache.get(3));  // returns 3
        System.out.println("get(4): " + cache.get(4));  // returns 4
    }
}