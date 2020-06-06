package ru.chori.deque.concurrent

class InternalDequeue<T> : AbstractMutableCollection<T>() {
    override val size: Int
        get() = _size

    /**
     * This method proxies to [addFirst]
     */
    override fun add(element: T): Boolean {
        addFirst(element)
        return true
    }

    override fun iterator(): MutableIterator<T> {
        return Iterator()
    }

    fun iteratorToLast(): MutableIterator<T> {
        return Iterator(tail)
    }

    fun addFirst(element: T) {
        val current: Node<T> = Node(element, null, head)
        head?.prev = current

        if (head == null) tail = current

        head = current
        _size++
    }

    fun addLast(element: T) {
        val current: Node<T> = Node(element, tail, null)
        tail?.next = current

        if (tail == null) head = current

        tail = current
        _size++
    }

    private data class Node<T>(val data: T, var prev: Node<T>? = null, var next: Node<T>? = null)

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size: Int = 0

    private inner class Iterator(var current: Node<T>? = head) : MutableIterator<T> {
        override fun hasNext(): Boolean = current != null

        override fun next(): T {
            val element: T? = current?.data
            current = current?.next

            return element ?: throw NoSuchElementException()
        }

        override fun remove() {
            current?.prev?.next = current?.next
            current?.next?.prev = current?.prev

            if (current == head) head = current?.next
            if (current == tail) tail = current?.prev
        }
    }
}
