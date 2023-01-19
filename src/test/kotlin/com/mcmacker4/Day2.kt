package com.mcmacker4

import org.junit.jupiter.api.Test

class Day2 : AdventDay(2) {

    enum class Hand(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        companion object {
            fun of(value: String): Hand {
                if (value == "A" || value == "X")
                    return ROCK
                if (value == "B" || value == "Y")
                    return PAPER
                if (value == "C" || value == "Z")
                    return SCISSORS
                throw Exception("'$value' is not a Hand")
            }
        }
    }

    private val wins = mapOf(
        Pair(Hand.ROCK, Hand.SCISSORS),
        Pair(Hand.PAPER, Hand.ROCK),
        Pair(Hand.SCISSORS, Hand.PAPER)
    )

    private val losses = mapOf(
        Pair(Hand.ROCK, Hand.PAPER),
        Pair(Hand.PAPER, Hand.SCISSORS),
        Pair(Hand.SCISSORS, Hand.ROCK)
    )

    private fun score(round: List<Hand>) = round[1].score + roundScore(round[0], round[1])

    private fun roundScore(left: Hand, right: Hand): Int {
        if (wins[right] == left)
            return 6
        if (right == left)
            return 3
        return 0
    }

    @Test
    fun part1() {
        val score = getInputLines().map { it.split(" ").map(Hand::of) }.sumOf(::score)
        log("Part 1: $score points")
    }

    private fun calculateRound(left: Hand, outcome: String): List<Hand> {
        return when (outcome) {
            "X" -> listOf(left, wins[left]!!)
            "Y" -> listOf(left, left)
            else -> listOf(left, losses[left]!!)
        }
    }

    @Test
    fun part2() {
        val score = getInputLines()
            .map { it.split(" ") }
            .map { calculateRound(Hand.of(it[0]), it[1]) }
            .map(::score)
            .sum()
        log("Part 2: $score points")
    }

}