package ru.chori.deque.concurrent

/**
 * Blocking deque based on doubly linked list. The class is an adapter to the internal deque, proxying all operations to
 * it. Getter of [deque] is synchronized, so concurrent thread will have to wait, until another thread stop performing
 * it's operation. This guarantees that before thread accesses the [deque], [deque] will be in consistent state.
 */
class ConcurrentLinkedDeque<T> : ConcurrentDeque<T> {
    override val isEmpty: Boolean
        get() = deque.isEmpty()
    override val size: Int
        get() = deque.size

    override fun clear() {
        deque.clear()
    }

    override fun addFirst(value: T) {
        deque.addFirst(value)
    }

    override fun addLast(value: T) {
        deque.addLast(value)
    }

    override fun peekFirst(): T? {
        val iterator: MutableIterator<T> = deque.iterator()

        return if (iterator.hasNext()) iterator.next() else null
    }

    override fun peekLast(): T? {
        val iterator: MutableIterator<T> = deque.iteratorToLast()

        return if (iterator.hasNext()) iterator.next() else null
    }

    override fun pollFirst(): T? {
        val iterator: MutableIterator<T> = deque.iterator()

        if (!iterator.hasNext()) return null

        val element: T = iterator.next()
        iterator.remove()

        return element
    }

    override fun pollLast(): T? {
        val iterator: MutableIterator<T> = deque.iteratorToLast()

        if (!iterator.hasNext()) return null

        val element: T = iterator.next()
        iterator.remove()

        return element
    }

    override fun contains(value: T): Boolean = deque.contains(value)

    private val deque: InternalDeque<T> = InternalDeque()
        @Synchronized get
}
