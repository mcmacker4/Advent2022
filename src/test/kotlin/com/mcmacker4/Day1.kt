package com.mcmacker4

import org.junit.jupiter.api.Test

class Day1 : AdventDay(1) {

    @Test
    fun execute() {
        val calories = getInput()
            .split("\n\n")
            .map {
                it.split("\n")
                    .map(String::toInt)
                    .sum()
            }
            .sorted()
            .reversed()
            .subList(0, 3)
            .sum()
        log("$calories calories")
    }

}