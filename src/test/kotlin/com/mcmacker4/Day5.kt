package com.mcmacker4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import java.util.regex.Pattern

class Day5 : AdventDay(5) {

    private fun parseStackLine(line: String) = List(line.length / 4 + 1) {
        line[1 + it * 4]
    }

    private fun parseHeader(stackMarkers: String, lines: List<String>): List<LinkedList<Char>> {
        val stackCount = stackMarkers.split(Pattern.compile("\\s+")).count { it.trim().isNotEmpty() }
        val stacks = List(stackCount) { LinkedList<Char>() }

        lines.map(::parseStackLine).forEach {
            it.forEachIndexed { i, crate ->
                if (!crate.isWhitespace()) {
                    stacks[i].addLast(crate)
                }
            }
        }

        return stacks
    }

    data class Move(val from: Int, val to: Int, val count: Int) {
        companion object {
            fun parse(line: String): Move {
                val parts = line.split(" ")
                return Move(parts[3].toInt() - 1, parts[5].toInt() - 1, parts[1].toInt())
            }
        }
    }

    private fun executeMove9000(move: Move, stacks: List<LinkedList<Char>>) {
        for (i in 0 until move.count) {
            val crate = stacks[move.from].pop()
            stacks[move.to].push(crate)
        }
    }

    private fun resolveProblem(moveExecutor: (Move, List<LinkedList<Char>>) -> Unit): String {
        val parts = getInput().split("${System.lineSeparator()}${System.lineSeparator()}")

        val headerLines = parts[0].lines()
        val stackMarkers = headerLines.last()
        val stackLines = headerLines.dropLast(1)

        val movesText = parts[1].lines()

        val stacks: List<LinkedList<Char>> = parseHeader(stackMarkers, stackLines)

        movesText.map(Move::parse).forEach {
            moveExecutor(it, stacks)
        }

        return stacks.map(LinkedList<Char>::peek).joinToString("", transform = Char::toString)

    }

    @Test
    fun part1() {
        val result = resolveProblem(::executeMove9000)
        log("Part 1: $result")
        assertEquals("ZBDRNPMVH", result)
    }

    private fun executeMove9001(move: Move, stacks: List<LinkedList<Char>>) {
        val intermediate = LinkedList<Char>()
        for (i in 0 until move.count) {
            intermediate.push(stacks[move.from].pop())
        }
        for (i in 0 until move.count) {
            stacks[move.to].push(intermediate.pop())
        }
    }

    @Test
    fun part2() {
        val result = resolveProblem(::executeMove9001)
        log("Part 2: $result")
        assertEquals("WDLPFNNNB", result)
    }

}