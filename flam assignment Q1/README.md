Data Structures Used:
HashMap: Stores key-value pairs for O(1) access time

Doubly Linked List: Maintains the order of usage with head being most recently used and tail being least recently used

Key Operations:
get(key):

If key exists in cache:

Move the corresponding node to the head (most recently used)

Return the value

Else return -1

put(key, value):

If key exists:

Update the value

Move the node to head

If key doesn't exist:

Create new node

Add to hashmap

Add to head of linked list

If capacity exceeded:

Remove tail node (LRU)

Remove from hashmap

Helper Methods:
addNode(node): Adds node right after head

removeNode(node): Removes node from its current position

moveToHead(node): Combines remove and add to move node to head

popTail(): Removes and returns the tail node (LRU)

Time Complexity:
Both get() and put() operations are O(1) due to:

HashMap provides O(1) access

Doubly linked list provides O(1) insertion/deletion at both ends

Space Complexity:
O(capacity) - We store at most 'capacity' number of items

Example Walkthrough
Using the provided example:
LRUCache lru(2);
lru.put(1, 1); // Cache: {1=1}
lru.put(2, 2); // Cache: {2=2, 1=1}
lru.get(1);     // Returns 1, Cache: {1=1, 2=2}
lru.put(3, 3);  // Evicts 2, Cache: {3=3, 1=1}
lru.get(2);     // Returns -1 (not found)
lru.put(4, 4);  // Evicts 1, Cache: {4=4, 3=3}
lru.get(1);     // Returns -1 (not found)
lru.get(3);     // Returns 3, Cache: {3=3, 4=4}
lru.get(4);     // Returns 4, Cache: {4=4, 3=3}