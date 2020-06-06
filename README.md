# KTorTestTask
ConcurrentDeque: a test task for KTor summer internship

## Implementation
The implementation is straightforward. The deque uses a doubly-linked list to keep all elements. Thus, all operations 
(except `contains`) will run in `O(1)` time complexity.

Synchronization is achieved due to using `@Synchronized` annotation on every method of the deque. This is the slowest, yet very
reliable solution, because it's guaranteed that the deque will never be in inconsistent state, because it is always 
automatically being locked by mutex on every operation.
