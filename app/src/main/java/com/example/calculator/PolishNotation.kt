package com.example.calculator

import java.util.*

class PolishNotation {

    private var stackNumbers: Stack<Int> = Stack()
    private var stackOperations: Stack<String> = Stack()

    private fun getPriority(tok: String): Int {
        when (tok) {
            "-", "+" -> return 1
            "*", "/" -> return 2
        }
        return 0
    }
    fun createStack(tok: ArrayList<String>): Int {
        for (token in tok) {
            if (isDigit(token)) {
                stackNumbers.push(token.toInt())
            } else if (isBrackets(token)) {
                // описать логику скобок
                if (token == "(") {
                    stackOperations.push(token)
                } else {
                    while (!stackOperations.peek().equals("(")) {
                        val a = mathOperation(
                            stackNumbers.pop(),
                            stackNumbers.pop(),
                            stackOperations.pop()
                        )
                        stackNumbers.push(a)
                    }
                    if (stackOperations.peek().equals("(")) {
                        stackOperations.pop()
                    }
                }
            } else {
                //логика операндов
                if (stackOperations.isEmpty() || getPriority(stackOperations.peek()) < getPriority(
                        token
                    )
                ) {
                    stackOperations.push(token)
                } else if (getPriority(stackOperations.peek()) >= getPriority(token)) {
                    val b =
                        mathOperation(stackNumbers.pop(), stackNumbers.pop(), stackOperations.pop())
                    stackOperations.push(token)
                    stackNumbers.push(b)
                }
            }
        }
        while (!stackOperations.isEmpty() && stackNumbers.size > 1) {
            val b =
                mathOperation(stackNumbers.pop(), stackNumbers.pop(), stackOperations.pop())
            stackNumbers.push(b)
            println("==========================")
            println("операции$stackOperations")
            println("числа$stackNumbers")
            println("==========================")
        }
        return stackNumbers.peek()
    }
    private fun mathOperation(b: Int, a: Int, operation: String): Int {
        return when (operation) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> 0
        }
    }

    private fun isBrackets(s: String): Boolean {
        return s == ")" || s == "("
    }

    @Throws(NumberFormatException::class)
    private fun isDigit(s: String): Boolean {
        return try {
            s.toInt()
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}