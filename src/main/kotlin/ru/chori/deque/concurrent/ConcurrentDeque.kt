package ru.chori.deque.concurrent

interface ConcurrentDeque<T> {
    /**
     * Checks if this deque is empty.
     */
    val isEmpty: Boolean

    /**
     * The number of elements in this deque
     */
    val size: Int

    /**
     * Removes all elements from this deque.
     */
    fun clear()

    /**
     * Pushes [value] to the front of this deque.
     */
    fun addFirst(value: T)

    /**
     * Pushes [value] to the back of this deque.
     */
    fun addLast(value: T)

    /**
     * Returns the first element of this deque, or null if it is empty.
     */
    fun peekFirst(): T?

    /**
     * Returns the last element of this deque, or null if it is empty.
     */
    fun peekLast(): T?

    /**
     * Removes the first element from this deque and returns it.
     * Returns null if the deque is empty.
     */
    fun pollFirst(): T?

    /**
     * Removes the last element from this deque and returns it.
     * Returns null if the deque is empty.
     */
    fun pollLast(): T?

    /**
     * Checks if this deque contains the given [value].
     */
    fun contains(value: T): Boolean
}
