package com.example.calculator

import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class PolishNotation {

    private var stackNumbers: Stack<Double> = Stack()
    private var stackOperations: Stack<String> = Stack()

    //проверка явлиется ли строка числом
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
     * функция принимает ArrayList
     * пример 10+10-(10-12*20)
     * возвращает результат Double
     */
    fun calculator(tok: ArrayList<String>): Double {
        try {
            for (token in tok) {

                //если число добавляем его в стек.
                if (isDigit(token)) {
                    stackNumbers.push(token.toDouble())

                    // логика при обнаружении скобок
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

                    //логика с операндами
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

            //после прохода по выражению выполняем операции в стеке
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

    //Возвращает приоритет математических операций
    private fun getPriority(tok: String): Int {
        when (tok) {
            "-", "+" -> return 1
            "*", "/" -> return 2
            "^", "sqrt", "sqr" -> return 3
        }
        return 0
    }

    //является ли введенная строка скабками
    private fun isBrackets(s: String): Boolean {
        return s == ")" || s == "("
    }

    //выполняем математические выражения
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