Data Structure Design
Buckets Array: An array where each element is the head of a linked list

Node Class: Represents key-value pairs and handles collisions via chaining

Key Operations
put(key, value):

Calculates bucket index using hash function (key % array length)

Checks if key exists in the chain (updates if found)

Adds new node to chain if key doesn't exist

Resizes array if load factor is exceeded

get(key):

Calculates bucket index

Searches chain for matching key

Returns value if found, -1 otherwise

remove(key):

Calculates bucket index

Searches chain for matching key

Removes node by adjusting pointers in linked list

Performance Characteristics
Average Case: O(1) for all operations due to:

Good hash distribution (simple modulo)

Resizing when load factor is exceeded

Linked list operations at bucket level

Worst Case: O(n) when all keys hash to same bucket (unlikely with proper resizing)