package com.mcmacker4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day4 : AdventDay(4) {

    private fun lineToRanges(line: String): Pair<IntRange, IntRange> {
        val list = line.split(",").map(::parseRange)
        return Pair(list[0], list[1])
    }

    private fun parseRange(value: String): IntRange {
        val values = value.split("-").map(String::toInt)
        return IntRange(values[0], values[1])
    }

    private fun isFullyContained(big: IntRange, small: IntRange) =
        big.first <= small.first && big.last >= small.last

    @Test
    fun part1() {
        val count = getInputLines().map(::lineToRanges).map {
            isFullyContained(it.first, it.second) || isFullyContained(it.second, it.first)
        }.count { it }
        log("Part 1: $count")
        assertEquals(431, count)
    }

    private fun overlap(left: IntRange, right: IntRange) =
        left.first <= right.last && right.first <= left.last

    @Test
    fun part2()  {
        val count = getInputLines().map(::lineToRanges).map {
            overlap(it.first, it.second)
        }.count { it }
        log("Part 2: $count")
        assertEquals(823, count)
    }

}