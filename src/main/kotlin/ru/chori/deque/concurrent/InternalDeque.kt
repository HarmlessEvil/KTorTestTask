package ru.chori.deque.concurrent

/**
 * Synchronous deque, that supports querying first and last element
 */
class InternalDeque<T> : AbstractMutableCollection<T>() {
    override val size: Int
        get() = _size

    /**
     * This method proxies to [addFirst]. This might mean that "natural" order of inserting elements is to this end of
     * deque.
     */
    override fun add(element: T): Boolean {
        addFirst(element)
        return true
    }

    override fun iterator(): MutableIterator<T> {
        return Iterator()
    }

    /**
     * Iterator to most recently added element using [addLast] or least recently – using [addFirst].
     */
    fun iteratorToLast(): MutableIterator<T> {
        return Iterator(tail)
    }

    /**
     * Adds [element] to beginning of the deque.
     */
    fun addFirst(element: T) {
        val current: Node<T> = Node(element, null, head)
        head?.prev = current

        if (head == null) tail = current

        head = current
        _size++
    }

    /**
     * Adds [element] to the end of deque.
     */
    fun addLast(element: T) {
        val current: Node<T> = Node(element, tail, null)
        tail?.next = current

        if (tail == null) head = current

        tail = current
        _size++
    }

    private data class Node<T>(val data: T, var prev: Node<T>? = null, var next: Node<T>? = null) {
        override fun toString(): String {
            return "Node(data=$data)"
        }
    }

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size: Int = 0

    private inner class Iterator(var current: Node<T>? = head) : MutableIterator<T> {
        var last: Node<T>? = null

        override fun hasNext(): Boolean = current != null

        override fun next(): T {
            val element: T? = current?.data

            last = current
            current = current?.next

            return element ?: throw NoSuchElementException()
        }

        override fun remove() {
            if (last == null) throw IllegalStateException()

            last?.prev?.next = last?.next
            last?.next?.prev = last?.prev

            if (last == head) head = last?.next
            if (last == tail) tail = last?.prev

            _size--
            last = null
        }
    }
}
