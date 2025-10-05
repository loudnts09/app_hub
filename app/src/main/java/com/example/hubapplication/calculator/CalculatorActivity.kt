package com.example.hubapplication.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.hubapplication.calculator.Calculator
import com.example.hubapplication.R
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private lateinit var tvHistorico: TextView
    private val calc = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvDisplay = findViewById(R.id.txtResultado)
        tvHistorico = findViewById(R.id.txtHistorico)
        updateDisplay()

        savedInstanceState?.let {
            calc.fromBundle(it)
            updateDisplay()
        }

        // helper seguro (não quebra se o ID não existir)
        fun on(id: Int, action: () -> Unit) {
            findViewById<View?>(id)?.setOnClickListener { action() }
        }

        // Dígitos
        val digits = listOf(
            R.id.btn0 to '0', R.id.btn1 to '1', R.id.btn2 to '2', R.id.btn3 to '3',
            R.id.btn4 to '4', R.id.btn5 to '5', R.id.btn6 to '6',
            R.id.btn7 to '7', R.id.btn8 to '8', R.id.btn9 to '9'
        )
        digits.forEach { (id, d) -> on(id) { calc.inputDigit(d); updateDisplay() } }

        // Ponto e +/- (se existir no layout)
        on(R.id.btnPonto) { calc.inputDot(); updateDisplay() }
        on(R.id.btnSign) { calc.toggleSign(); updateDisplay() }

        // π e log10
        on(R.id.btnPi) { calc.inputConstantPI(); updateDisplay() }
        on(R.id.btnLog) { calc.log10Unary(); updateDisplay() }

        // Operadores binários
        on(R.id.btnSomar) { calc.setOperator(Calculator.Op.ADD); updateDisplay() }
        on(R.id.btnSubtrair) { calc.setOperator(Calculator.Op.SUB); updateDisplay() }
        on(R.id.btnMultiplicar) { calc.setOperator(Calculator.Op.MUL); updateDisplay() }
        on(R.id.btnDividir) { calc.setOperator(Calculator.Op.DIV); updateDisplay() }

        // Unários
        on(R.id.btnRaiz) { calc.sqrtUnary(); updateDisplay() }
        on(R.id.btnPorcentagem) { calc.percentOfOperand(); updateDisplay() }

        // Igual
        on(R.id.btnIgual) {
            val r = calc.equals()
            updateDisplay()
            if (r == "Erro") Toast.makeText(this, "Operação inválida", Toast.LENGTH_SHORT).show()
        }

        // Clear e Backspace
        on(R.id.btnClear) { calc.clearAll(); updateDisplay() }
        on(R.id.btnBackspace) { calc.backspace(); updateDisplay() }
    }

    private fun updateDisplay() {
        tvDisplay.text = calc.display()
        tvHistorico.text = calc.historyShort()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(calc.toBundle())
    }
}