package com.example.hubapplication.calculator

import android.os.Bundle
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

class Calculator {

    enum class Op(val symbol: String) {
        ADD("+"), SUB("-"), MUL("×"), DIV("÷"), POW("^")
    }

    private var currentInput: String = "0"
    private var operand: Double? = null
    private var pendingOp: Op? = null
    private var replaceOnNextDigit: Boolean = false

    private var memory: Double = 0.0
    private var lastExpression: String = ""

    fun inputDigit(d: Char) {
        require(d in '0'..'9')
        currentInput = when {
            replaceOnNextDigit -> { replaceOnNextDigit = false; d.toString() }
            currentInput == "0" -> d.toString()
            currentInput == "-0" -> "-$d"
            else -> currentInput + d
        }
    }

    fun inputDot() {
        if (replaceOnNextDigit) { replaceOnNextDigit = false; currentInput = "0."; return }
        if (!currentInput.contains('.')) {
            currentInput = if (currentInput.isEmpty() || currentInput == "-") currentInput + "0."
            else currentInput + "."
        }
    }

    fun toggleSign() {
        if (replaceOnNextDigit) replaceOnNextDigit = false
        currentInput = if (currentInput.startsWith("-")) currentInput.removePrefix("-")
        else if (currentInput == "0") "-0" else "-$currentInput"
    }

    fun inputConstantPI() { replaceOnNextDigit = false; currentInput = Math.PI.formatForDisplay() }

    fun setOperator(op: Op) {
        val value = currentInput.toDoubleOrNull() ?: return
        if (operand == null) operand = value
        else if (pendingOp != null) {
            val r = perform(operand!!, value, pendingOp!!)
            if (r.isNaN() || r.isInfinite()) { currentInput = "Erro"; operand = null; pendingOp = null; lastExpression = ""; replaceOnNextDigit = false; return }
            operand = r
        }
        pendingOp = op
        lastExpression = "${(operand ?: 0.0).formatForDisplay()} ${op.symbol}"
        currentInput = (operand ?: 0.0).formatForDisplay()
        replaceOnNextDigit = true
    }

    fun percentOfOperand() {
        val base = operand ?: 0.0
        val value = currentInput.toDoubleOrNull() ?: return
        val pct = base * (value / 100.0)
        replaceOnNextDigit = false
        currentInput = pct.formatForDisplay()
    }

    fun sqrtUnary() {
        val v = currentInput.toDoubleOrNull() ?: return
        val r = if (v < 0) Double.NaN else sqrt(v)
        replaceOnNextDigit = false
        currentInput = r.formatOrError()
    }

    fun log10Unary() {
        val v = currentInput.toDoubleOrNull() ?: return
        val r = if (v <= 0) Double.NaN else log10(v)
        replaceOnNextDigit = false
        currentInput = r.formatOrError()
    }

    fun powOperator() = setOperator(Op.POW)

    fun equals(): String {
        val b = currentInput.toDoubleOrNull() ?: return display()
        val a = operand
        val op = pendingOp
        val result = if (a != null && op != null) perform(a, b, op) else b
        lastExpression = if (a != null && op != null)
            "${a.formatForDisplay()} ${op.symbol} ${b.formatForDisplay()} = ${result.formatForDisplay()}"
        else b.formatForDisplay()
        operand = null; pendingOp = null
        currentInput = result.formatOrError()
        replaceOnNextDigit = true
        return currentInput
    }

    private fun perform(a: Double, b: Double, op: Op): Double = when (op) {
        Op.ADD -> a + b
        Op.SUB -> a - b
        Op.MUL -> a * b
        Op.DIV -> if (b == 0.0) Double.NaN else a / b
        Op.POW -> a.pow(b)
    }

    fun clearAll() { currentInput = "0"; operand = null; pendingOp = null; lastExpression = ""; replaceOnNextDigit = false }
    fun backspace() {
        if (replaceOnNextDigit) { replaceOnNextDigit = false; currentInput = "0"; return }
        currentInput = when {
            currentInput.length <= 1 -> "0"
            currentInput.length == 2 && currentInput.startsWith("-") -> "0"
            else -> currentInput.dropLast(1)
        }
    }

    fun memoryAdd() { memory += currentInput.toDoubleOrNull() ?: 0.0 }
    fun memorySub() { memory -= currentInput.toDoubleOrNull() ?: 0.0 }
    fun memoryRead() { replaceOnNextDigit = true; currentInput = memory.formatForDisplay() }
    fun memoryClear() { memory = 0.0 }

    fun display(): String = currentInput
    fun historyShort(): String = lastExpression

    fun toBundle(): Bundle = Bundle().apply {
        putString("currentInput", currentInput)
        putSerializable("pendingOp", pendingOp)
        putDouble("operand", operand ?: Double.NaN)
        putBoolean("hasOperand", operand != null)
        putString("lastExpression", lastExpression)
        putDouble("memory", memory)
        putBoolean("replaceOnNextDigit", replaceOnNextDigit)
    }
    fun fromBundle(b: Bundle) {
        currentInput = b.getString("currentInput", "0")
        pendingOp = b.getSerializable("pendingOp") as? Op
        operand = if (b.getBoolean("hasOperand")) b.getDouble("operand") else null
        lastExpression = b.getString("lastExpression", "")
        memory = b.getDouble("memory", 0.0)
        replaceOnNextDigit = b.getBoolean("replaceOnNextDigit", false)
    }
}

private fun Double.formatForDisplay(): String {
    if (this.isNaN() || this.isInfinite()) return "Erro"
    val asLong = this.toLong()
    return if (this == asLong.toDouble()) asLong.toString() else this.toString()
}
private fun Double.formatOrError(): String =
    if (this.isNaN() || this.isInfinite()) "Erro" else this.formatForDisplay()