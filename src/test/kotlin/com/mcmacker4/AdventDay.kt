package com.mcmacker4

import java.io.BufferedReader

abstract class AdventDay(private val day: Int) {

    fun getInput() = getInputReader().readText()

    fun getInputLines() = getInputReader().readLines()

    private fun getInputReader(): BufferedReader {
        return javaClass.getResourceAsStream("/inputs/day$day")?.bufferedReader()
            ?: throw Exception("Could not read Input for day $day")
    }

    protected fun log(text: String) {
        println("[Day $day] $text")
    }

    fun String.green() = "\u001b[92;1m${this}\u001b[0m"
    fun String.red() = "\u001b[91m${this}\u001b[0m"

}