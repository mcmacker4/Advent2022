package com.mcmacker4

import java.io.BufferedReader
import java.util.stream.Stream

abstract class AdventDay(private val day: Int) {

    fun getInput(): String {
        return getInputReader().readText()
    }

    fun getInputLinesStream(): Stream<String>? {
        return getInputReader().lines()
    }

    fun getInputLines(): List<String> {
        return getInputReader().readLines()
    }

    private fun getInputReader(): BufferedReader {
        return javaClass.getResourceAsStream("/inputs/day$day")?.bufferedReader()
            ?: throw Exception("Could not read Input for day $day")
    }

    protected fun log(text: String) {
        println("[Day $day] $text")
    }

}