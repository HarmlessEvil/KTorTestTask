package ru.chori.deque.concurrent

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import kotlin.random.Random

/**
 * This test spawns process, that asynchronously with tiny random delays will put 1'000 random integers to the deque.
 * Main process tries to poll 1'000 elements one by one and stops when he has enough. Then acquired values comparing to
 * ensure that implementation of [ConcurrentDeque] works correctly
 */
class ConcurrentLinkedDequeMultithreadingStressTest {
    companion object {
        const val STRESS_ITERATIONS: Int = 1000
    }

    @Test(timeout = 10_000)
    fun stressTest() = runBlocking(Executors.newFixedThreadPool(2).asCoroutineDispatcher()) {
        val actual: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        val expected: Deque<Int> = LinkedBlockingDeque()

        launch {
            for (i in 0 until STRESS_ITERATIONS) {
                val value: Int = Random.nextInt()

                expected.addFirst(value)
                actual.addFirst(value)

                delay(Random.nextLong(10))
            }
        }

        val expectedStorage: Deque<Int> = LinkedList()
        val actualStorage: Deque<Int> = LinkedList()

        var i = 0
        while (i < STRESS_ITERATIONS * 2) {
            val expectedItem: Int? = expected.pollLast()
            val actualItem: Int? = actual.pollLast()

            if (expectedItem != null) {
                expectedStorage.addFirst(expectedItem)
                i++
            }

            if (actualItem != null) {
                actualStorage.addFirst(actualItem)
                i++
            }
        }

        assertEquals(expectedStorage, actualStorage)
    }
}