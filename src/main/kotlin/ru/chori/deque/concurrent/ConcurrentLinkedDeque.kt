package ru.chori.deque.concurrent

import sun.misc.ConditionLock

/**
 * Blocking deque based on doubly linked list. The class is an adapter to the internal deque, proxying all operations to
 * it
 */
class ConcurrentLinkedDeque<T> : ConcurrentDeque<T> {
    override val isEmpty: Boolean
        @Synchronized get() = deque.isEmpty()
    override val size: Int
        @Synchronized get() = deque.size

    @Synchronized
    override fun clear() {
        deque.clear()
    }

    @Synchronized
    override fun addFirst(value: T) {
        deque.addFirst(value)
    }

    @Synchronized
    override fun addLast(value: T) {
        deque.addLast(value)
    }

    @Synchronized
    override fun peekFirst(): T? {
        val iterator: MutableIterator<T> = deque.iterator()

        return if (iterator.hasNext()) iterator.next() else null
    }

    @Synchronized
    override fun peekLast(): T? {
        val iterator: MutableIterator<T> = deque.iteratorToLast()

        return if (iterator.hasNext()) iterator.next() else null
    }

    @Synchronized
    override fun pollFirst(): T? {
        val iterator: MutableIterator<T> = deque.iterator()

        if (!iterator.hasNext()) return null

        val element: T = iterator.next()
        iterator.remove()

        return element
    }

    @Synchronized
    override fun pollLast(): T? {
        val iterator: MutableIterator<T> = deque.iteratorToLast()

        if (!iterator.hasNext()) return null

        val element: T = iterator.next()
        iterator.remove()

        return element
    }

    @Synchronized
    override fun contains(value: T): Boolean = deque.contains(value)

    private val deque: InternalDeque<T> = InternalDeque()
}
