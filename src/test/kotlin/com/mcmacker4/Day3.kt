package com.mcmacker4

import org.junit.jupiter.api.Test

class Day3 : AdventDay(3) {

    private val priority = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        .toCharArray().mapIndexed { i, c -> Pair(c, i + 1) }.toMap()

    @Test
    fun part1() {
        val score = getInputLines()
            .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
            .map { Pair(it.first.toHashSet(), it.second.toHashSet()) }
            .map { it.first.intersect(it.second).first() }
            .sumOf { priority[it]!! }
        log("Part 1: Priority $score")
    }

    @Test
    fun part2() {
        val lines = getInputLines()
        val score = IntRange(0, lines.size - 1).zip(lines)
            .groupBy({ it.first / 3 }, { it.second })
            .values
            .map(::findCommonItem)
            .sumOf { priority[it]!! }
        log("Part 2: Priority $score")
    }

    private fun findCommonItem(elves: List<String>): Char {
        return elves.map { it.toCharArray().toSet() }.reduce(Set<Char>::intersect).first()
    }

}