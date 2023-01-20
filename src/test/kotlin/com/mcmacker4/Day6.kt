package com.mcmacker4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.HashSet

class Day6 : AdventDay(6) {

    private fun resolveProblem(input: String, windowSize: Int): Int {
        val window = LinkedList<Char>()
        val windowSet = HashSet<Char>(windowSize)

        for (i in input.indices) {
            windowSet.clear()
            window.addLast(input[i])

            if (window.size > windowSize)
                window.pop()

            windowSet.addAll(window)
            if (windowSet.size == windowSize)
                return i + 1
        }

        throw Exception("Oopsie: end of code")
    }

    @Test
    fun part1() {
        val input = getInput()
        val result = resolveProblem(input, 4)
        log("Part 1: $result")
        assertEquals(1920, result)
    }

    @Test
    fun part2() {
        val input = getInput()
        val result = resolveProblem(input, 14)
        log("Part 2: $result")
        assertEquals(2334, result)
    }

}