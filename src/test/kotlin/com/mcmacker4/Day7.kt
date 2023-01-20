package com.mcmacker4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class Day7 : AdventDay(7) {

    sealed interface Node {
        val size: Int
    }

    class File(override val size: Int) : Node

    class Directory(val parent: Directory?) : Node {
        val children = hashMapOf<String, Node>()

        override val size: Int
            get() = children.values.sumOf(Node::size)

        fun addDir(name: String){
            children[name] = Directory(this)
        }

        fun addFile(name: String, size: Int) {
            children[name] = File(size)
        }

        fun findRecursive(predicate: (Node) -> Boolean): List<Node> {
            return children.values.flatMap {
                when (it) {
                    is Directory -> it.findRecursive(predicate) + listOf(it)
                    is File -> listOf(it)
                }
            }.filter(predicate) // Can be optimized, we are calling the predicate multiple times per node
        }

    }

    class InputParser(private val root: Directory, private val lines: LinkedList<String>) {

        private var workingDir: Directory = root

        fun run() {
            while (!lines.isEmpty()) {
                val line = lines.pop()
                when {
                    line.startsWith("$ ls") -> runLs()
                    line.startsWith("$ cd") -> runCd(line)
                    else -> throw Exception("Line '$line' is not a command")
                }
            }
        }

        private fun runLs() {
            while (!lines.isEmpty() && !lines.peek().startsWith("$")) {
                val parts = lines.pop().split(" ")
                when {
                    parts[0] == "dir" -> workingDir.addDir(parts[1])
                    else -> workingDir.addFile(parts[1], parts[0].toInt())
                }
            }
        }

        private fun runCd(line: String) {
            val name = line.split(" ")[2]
            workingDir = when (name) {
                "/" -> root
                ".." -> workingDir.parent!!
                else -> workingDir.children[name] as Directory
            }
        }

    }

    private fun getPreparedRoot(): Directory {
        val root = Directory(null)
        val lines = LinkedList(getInputLines())

        InputParser(root, lines).run()

        return root
    }

    @Test
    fun part1() {
        val maxSize = 100000
        val root = getPreparedRoot()

        val result = root.findRecursive { it is Directory && it.size < maxSize }.sumOf { it.size }

        log("Part 1: $result")

        assertEquals(1443806, result)
    }

    @Test
    fun part2() {
        val root = getPreparedRoot()
        val targetUsed = 70000000 - 30000000
        val used = root.size

        val sizeToDelete = used - targetUsed

        assertTrue(sizeToDelete > 0)

        val dirToDelete = root.findRecursive { it is Directory && it.size > sizeToDelete }.minBy(Node::size)

        val dirSize = dirToDelete.size

        println("Part 2: $dirSize")

        assertEquals(942298, dirSize)
    }

}