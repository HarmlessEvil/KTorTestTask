package ru.chori.deque.concurrent

import org.junit.Assert.*
import org.junit.Test
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.concurrent.Executors

/**
 * All operations except contains run in O(1) time, thus I don't know reliable way, how to test it
 */
class ConcurrentLinkedDequeMultithreadingTest {
    class SlowInteger(val x: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SlowInteger

            // Ensuring that contains will definitely be slow
            sleep(200)

            if (x != other.x) return false

            return true
        }

        override fun hashCode(): Int {
            return x
        }
    }

    @Test
    fun contains(): Unit = runBlocking(Executors.newFixedThreadPool(2).asCoroutineDispatcher()) {
        val deque: ConcurrentDeque<SlowInteger> = ConcurrentLinkedDeque()
        deque.addLast(SlowInteger(1))
        deque.addLast(SlowInteger(2))
        deque.addLast(SlowInteger(3))
        deque.addLast(SlowInteger(4))
        deque.addLast(SlowInteger(5))

        val contains = async {
            deque.contains(SlowInteger(5))
        }

        launch {
            deque.addFirst(SlowInteger(5))
            deque.pollLast()
        }

        assertTrue(contains.await())
    }
}
