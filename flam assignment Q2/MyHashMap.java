public class MyHashMap {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    
    private Node[] buckets;
    private int size;
    
    // Node class for linked list chaining
    private static class Node {
        int key;
        int value;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    public MyHashMap() {
        buckets = new Node[INITIAL_CAPACITY];
        size = 0;
    }
    
    public void put(int key, int value) {
        // Check if we need to resize
        if (size >= LOAD_FACTOR * buckets.length) {
            resize();
        }
        
        int index = getIndex(key);
        Node head = buckets[index];
        
        // Check if key already exists
        while (head != null) {
            if (head.key == key) {
                head.value = value; // Update existing value
                return;
            }
            head = head.next;
        }
        
        // Add new node at the beginning of the chain
        Node newNode = new Node(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }
    
    public int get(int key) {
        int index = getIndex(key);
        Node head = buckets[index];
        
        while (head != null) {
            if (head.key == key) {
                return head.value;
            }
            head = head.next;
        }
        
        return -1; // Key not found
    }
    
    public void remove(int key) {
        int index = getIndex(key);
        Node head = buckets[index];
        Node prev = null;
        
        while (head != null) {
            if (head.key == key) {
                if (prev == null) {
                    buckets[index] = head.next; // Remove first node
                } else {
                    prev.next = head.next; // Remove middle or last node
                }
                size--;
                return;
            }
            prev = head;
            head = head.next;
        }
    }
    
    private int getIndex(int key) {
        return key % buckets.length;
    }
    
    private void resize() {
        Node[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;
        
        // Rehash all existing entries
        for (Node head : oldBuckets) {
            while (head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }
}