package ru.chori.deque.concurrent

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import kotlin.random.Random

class ConcurrentLinkedDequeStressTest {
    companion object {
        const val STRESS_ITERATIONS: Int = 60_000
    }

    @Test
    fun stressTest() {
        val actual: ConcurrentDeque<Int> = ConcurrentLinkedDeque()
        val expected: Deque<Int> = LinkedBlockingDeque()

        for (i in 0 until STRESS_ITERATIONS) {
            when (Random.nextInt(5)) {
                0 -> {
                    val value: Int = Random.nextInt()

                    actual.addFirst(value)
                    expected.addFirst(value)
                }
                1 -> {
                    val value: Int = Random.nextInt()

                    actual.addLast(value)
                    expected.addLast(value)
                }
                2 -> {
                    val value: Int = Random.nextInt()

                    assertEquals(expected.contains(value), actual.contains(value))
                }
                3 -> {
                    assertEquals(expected.pollFirst(), actual.pollFirst())
                }
                4 -> {
                    assertEquals(expected.pollLast(), actual.pollLast())
                }
            }

            assertTrue(expected.stream().allMatch {
                actual.contains(it)
            })
        }
    }
}
