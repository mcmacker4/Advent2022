package com.mcmacker4

import org.junit.jupiter.api.Test

class Day1 : AdventDay(1) {

    private fun groupedCalories() = getInput()
        .split("\n\n")
        .map {
            it.split("\n")
                .map(String::toInt)
                .sum()
        }

    @Test
    fun part1() {
        val calories = groupedCalories().max()
        log("Part 1: $calories calories")
    }

    @Test
    fun part2() {
        val calories = groupedCalories()
            .sortedDescending()
            .subList(0, 3)
            .sum()
        log("Part 2: $calories calories")
    }

}