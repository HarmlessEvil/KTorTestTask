package ru.chori.deque.concurrent

class ConcurrentLinkedDeque<T> : ConcurrentDeque<T> {
    override val isEmpty: Boolean
        get() = list.isEmpty()
    override val size: Int
        get() = list.size

    override fun clear() {
        list.clear()
    }

    override fun addFirst(value: T) {
        list.addFirst(value)
    }

    override fun addLast(value: T) {
        list.addLast(value)
    }

    override fun peekFirst(): T? {
        val iterator: MutableIterator<T> = list.iterator()

        return if (iterator.hasNext()) iterator.next() else null
    }

    override fun peekLast(): T? {
        val iterator: MutableIterator<T> = list.iteratorToLast()

        return if (iterator.hasNext()) iterator.next() else null
    }

    override fun pollFirst(): T? {
        val iterator: MutableIterator<T> = list.iterator()

        if (!iterator.hasNext()) return null

        val element: T = iterator.next()
        iterator.remove()

        return element
    }

    override fun pollLast(): T? {
        val iterator: MutableIterator<T> = list.iteratorToLast()

        if (!iterator.hasNext()) return null

        val element: T = iterator.next()
        iterator.remove()

        return element
    }

    override fun contains(value: T): Boolean = list.contains(value)

    private val list: InternalDequeue<T> = InternalDequeue()
        @Synchronized get
}
