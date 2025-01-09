package com.example.lab1_dec18_rinku

class Calculator2 {
    private val inputs = mutableListOf<String>()
    private val history = mutableListOf<String>()

    fun push(value: String) {
        inputs.add(value)
    }

    fun calculate(): Int {
        if (inputs.isEmpty()) return 0

        var result = inputs[0].toIntOrNull() ?: return 0
        var currentOperator = ""

        for (i in 1 until inputs.size) {
            val value = inputs[i]

            if (value in listOf("+", "-", "*", "/")) {
                currentOperator = value
            } else {
                val operand = value.toIntOrNull() ?: continue

                result = when (currentOperator) {
                    "+" -> result + operand
                    "-" -> result - operand
                    "*" -> result * operand
                    "/" -> result / operand
                    else -> result
                }
            }
        }

        history.add(inputs.joinToString(" ") + " = $result")
        return result
    }

    fun getHistory(): String = history.joinToString("\n")

    fun clear() {
        inputs.clear()
    }
}
