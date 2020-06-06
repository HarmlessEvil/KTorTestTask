package ru.chori.deque.concurrent

import org.junit.Test

import org.junit.Assert.*

class ConcurrentLinkedDequeSynchronizedTest {
    @Test
    fun isEmpty() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()

        assertTrue(deque.isEmpty)
    }

    @Test
    fun isNotEmptyAfterAdd() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(1)

        assertFalse(deque.isEmpty)
    }

    @Test
    fun isEmptyAfterClear() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(1)
        deque.addFirst(2)
        deque.addLast(3)

        deque.clear()
        assertTrue(deque.isEmpty)
    }

    @Test
    fun isEmptyOnlyWhenSizeIs0() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()

        assertTrue(deque.isEmpty)
        assertEquals(0, deque.size)

        deque.addLast(5)
        assertTrue(deque.size > 0)
        assertFalse(deque.isEmpty)
    }

    @Test
    fun getSize() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()

        assertEquals(0, deque.size)
    }

    @Test
    fun getSizeAfterModification() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(3)
        deque.addLast(4)
        deque.addLast(5)

        assertEquals(3, deque.size)

        deque.addFirst(7)
        deque.addFirst(8)
        deque.pollLast()

        assertEquals(4, deque.size)
    }

    @Test
    fun clear() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.clear()

        assertTrue(deque.isEmpty)
    }

    @Test
    fun clearAfterModification() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(3)
        deque.addLast(4)
        deque.addLast(5)
        deque.pollLast()

        deque.clear()

        assertTrue(deque.isEmpty)
    }

    @Test
    fun addFirst() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(1)

        assertEquals(1, deque.peekFirst())
    }

    @Test
    fun addFirstMultipleTimes() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(1)
        deque.addFirst(2)
        deque.addFirst(2)
        deque.addFirst(4)

        assertEquals(4, deque.peekFirst())
    }

    @Test
    fun addLast() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(5)

        assertEquals(5, deque.peekLast())
    }

    @Test
    fun addLastMultipleTimes() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(1)
        deque.addLast(1)
        deque.addLast(2)
        deque.addLast(8)

        assertEquals(8, deque.peekLast())
    }

    @Test
    fun peekFirstEqualsPeekLastWhenThereIsOnlyOneItem() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(7)

        assertEquals(7, deque.peekLast())
        assertEquals(deque.peekLast(), deque.peekFirst())
    }

    @Test
    fun peekFirstMayPeekElementAddedLastInReverseOrder() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(3)
        deque.addLast(2)
        deque.addLast(1)

        assertEquals(1, deque.peekLast())
        assertEquals(3, deque.peekFirst())
    }

    @Test
    fun peekLastMayPeekElementAddedFirstInReverseOrder() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(3)
        deque.addFirst(2)
        deque.addFirst(1)

        assertEquals(1, deque.peekFirst())
        assertEquals(3, deque.peekLast())
    }

    @Test
    fun pollFirst() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(3)
        deque.addFirst(4)

        assertEquals(4, deque.pollFirst())
        assertEquals(1, deque.size)
    }

    @Test
    fun pollFirstWhenAddLast() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(7)
        deque.addLast(3)

        assertEquals(7, deque.pollFirst())
        assertEquals(3, deque.pollFirst())
    }

    @Test
    fun pollLast() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(0)
        deque.addLast(6)

        assertEquals(6, deque.pollLast())
        assertEquals(1, deque.size)
    }

    @Test
    fun contains() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        assertFalse(deque.contains(10))
    }

    @Test
    fun containsAfterAddLast() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(3)

        assertTrue(deque.contains(3))
    }

    @Test
    fun containsAfterAddFirst() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addFirst(3)

        assertTrue(deque.contains(3))
    }

    @Test
    fun containsAfterModifications() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(3)
        deque.addLast(5)
        deque.addFirst(10)
        deque.addLast(1)

        assertTrue(deque.contains(5))
    }

    @Test
    fun notContainsAfterRemove() {
        val deque: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        deque.addLast(3)
        deque.addLast(5)
        deque.addFirst(10)
        deque.addLast(1)

        deque.pollLast()
        deque.pollLast()

        deque.addFirst(1)

        assertFalse(deque.contains(5))
    }
}