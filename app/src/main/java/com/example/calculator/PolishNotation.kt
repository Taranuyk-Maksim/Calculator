package com.example.calculator

import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class PolishNotation {

    private var stackNumbers: Stack<Double> = Stack()
    private var stackOperations: Stack<String> = Stack()

    //check if the string is a number
    @Throws(NumberFormatException::class)
    private fun isDigit(s: String): Boolean {
        return try {
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * function accepts an ArrayList
     * example 10 + 10- (10-12 * 20)
     * returns the result of Double
     */
    fun calculator(tok: ArrayList<String>): Double {
        try {
            for (token in tok) {

                //if the number is added to the stack.
                if (isDigit(token)) {
                    stackNumbers.push(token.toDouble())

                    //parenthesis detection logic
                } else if (isBrackets(token)) {
                    if (token == "(") {
                        stackOperations.push(token)
                    } else {
                        while (stackOperations.peek() != "(") {
                            stackNumbers.push(
                                mathOperation(
                                    stackNumbers.pop(),
                                    stackNumbers.pop(),
                                    stackOperations.pop()
                                )
                            )
                        }
                        if (stackOperations.peek() == "(") {
                            stackOperations.pop()
                        }
                    }

                    //logic with operands
                } else {
                    if (stackOperations.isEmpty() || getPriority(stackOperations.peek()) < getPriority(
                            token
                        )
                    ) {
                        stackOperations.push(token)
                    } else if (getPriority(stackOperations.peek()) >= getPriority(token)) {
                        stackNumbers.push(
                            mathOperation(
                                stackNumbers.pop(),
                                stackNumbers.pop(),
                                stackOperations.pop()
                            )
                        )
                        stackOperations.push(token)
                    }
                }
            }

            //after passing through the expression, we perform operations on the stack
            while (!stackOperations.isEmpty() && stackNumbers.size >= 1) {
                try {
                    val b =
                        mathOperation(stackNumbers.pop(), stackNumbers.pop(), stackOperations.pop())
                    stackNumbers.push(b)
                } catch (ex: EmptyStackException) {
                    val b =
                        mathOperation(stackNumbers.pop(), 0.0, stackOperations.pop())
                    stackNumbers.push(b)
                }
            }
            return stackNumbers.peek()
        } catch (ex: EmptyStackException) {
            print("Invalid math expression answer - ")
        }
        return 0.0
    }

    //Returns the priority of math operations
    private fun getPriority(tok: String): Int {
        when (tok) {
            "-", "+" -> return 1
            "*", "/" -> return 2
            "^", "sqrt", "sqr" -> return 3
        }
        return 0
    }

    //whether the entered string is brackets
    private fun isBrackets(s: String): Boolean {
        return s == ")" || s == "("
    }

    //performs math expressions
    private fun mathOperation(
        a: Double,
        b: Double,
        operation: String
    ): Double? {
        return when (operation) {
            "+" -> b + a
            "-" -> b - a
            "*" -> b * a
            "/" -> b / a
            "^" -> b.pow(a)
            "sqrt" -> sqrt(a)
            "sqr" -> b.pow(1.0 / a)
            else -> 0.0
        }
    }
}