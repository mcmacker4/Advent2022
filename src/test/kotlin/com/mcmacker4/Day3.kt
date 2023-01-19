package com.mcmacker4

import org.junit.jupiter.api.Test

class Day3 : AdventDay(3) {

    private val abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private val score = abc.toCharArray().mapIndexed { i, c -> Pair(c, i + 1) }.toMap()

    @Test
    fun part1() {
        val score = getInputLines()
            .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
            .map { Pair(it.first.toHashSet(), it.second.toHashSet()) }
            .map { it.first.intersect(it.second).first() }
            .sumOf { score[it]!! }
        log("Part 1: Priority $score")
    }

}