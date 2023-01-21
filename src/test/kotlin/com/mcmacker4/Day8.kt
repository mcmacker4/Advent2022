package com.mcmacker4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8 : AdventDay(8) {

    data class Pos(val x: Int, val y: Int) {
        val right: Pos get() = Pos(x + 1, y)
        val left: Pos get() = Pos(x - 1, y)
        val up: Pos get() = Pos(x, y - 1)
        val down: Pos get() = Pos(x, y + 1)

        override fun toString() = "Pos($x, $y)"
    }

    class Forest(val width: Int, val height: Int, private val trees: IntArray) {

        fun get(pos: Pos) =
            trees[pos.x + pos.y * width]

        fun countVisibles() = setOf(Pos(0, 0), Pos(width - 1, 0), Pos(0, height - 1), Pos(width - 1, height - 1)) +
                countVisiblesFromDirection(Pos(1, 1), Pos::right, Pos::down, Pos::up) +
                countVisiblesFromDirection(Pos(1, 1), Pos::down, Pos::right, Pos::left) +
                countVisiblesFromDirection(Pos(width - 2, height - 2), Pos::up, Pos::left, Pos::right) +
                countVisiblesFromDirection(Pos(width - 2, height - 2), Pos::left, Pos::up, Pos::down)

        private fun countVisiblesFromDirection(startPos: Pos, bigStep: (Pos) -> Pos, smallStep: (Pos) -> Pos, heightProbe: (Pos) -> Pos): Set<Pos> {
            var currentStartPos = startPos
            val visibleTrees = hashSetOf<Pos>()
            while (!isPerimeter(currentStartPos)) {
                visibleTrees += countVisiblesInRow(heightProbe(currentStartPos), currentStartPos, smallStep)
                currentStartPos = bigStep(currentStartPos)
            }
            return visibleTrees
        }

        private fun countVisiblesInRow(startNeighbor: Pos, startPos: Pos, smallStep: (Pos) -> Pos): Set<Pos> {
            var maxSeenHeight = get(startNeighbor)
            var currentPos = startPos

            val visibleTrees = hashSetOf(startNeighbor)

            while (!isPerimeter(currentPos)) {
                val currentHeight = get(currentPos)
                if (currentHeight > maxSeenHeight) {
                    visibleTrees += currentPos
                    maxSeenHeight = currentHeight
                }
                currentPos = smallStep(currentPos)
            }

            return visibleTrees
        }

        private fun isPerimeter(pos: Pos) =
            pos.x == 0 || pos.x == width - 1 || pos.y == 0 || pos.y == height - 1

    }

    private fun parseForest(lines: List<String>): Forest {
        val width = lines.first().length
        val height = lines.size

        val trees = IntArray(width * height)

        repeat(height) { y ->
            val line = lines[y].toCharArray()
            repeat(width) { x ->
                trees[x + y * width] = line[x].digitToInt()
            }
        }

        return Forest(width, height, trees)
    }

    @Suppress("unused")
    private fun printForest(forest: Forest, visibles: Set<Pos>) {
        repeat(forest.height) { y ->
            repeat(forest.width) { x ->
                val pos = Pos(x, y)
                val height = forest.get(pos).toString()
                if (visibles.contains(pos))
                    print(height.green())
                else
                    print(height.red())
            }
            println()
        }
    }

    @Test
    fun part1() {
        val lines = getInputLines()

        val forest = parseForest(lines)
        val visibles = forest.countVisibles()

        // printForest(forest, visibles)

        log("Part 1: ${visibles.size}")
        assertEquals(1538, visibles.size)
    }

    @Test
    fun part2() {

    }

}