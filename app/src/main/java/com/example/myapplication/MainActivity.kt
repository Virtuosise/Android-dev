package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var Input: TextView
    private lateinit var Output: TextView
    private var currentN = ""
    private var OneN = 0.0
    private var currentOp = ""
    private var NewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Input = findViewById(R.id.input)
        Output = findViewById(R.id.output)


        setBNumbers()


        findViewById<TextView>(R.id.bp).setOnClickListener { setOperation("+") }
        findViewById<TextView>(R.id.bm).setOnClickListener { setOperation("-") }
        findViewById<TextView>(R.id.bmn).setOnClickListener { setOperation("*") }
        findViewById<TextView>(R.id.del).setOnClickListener { setOperation("/") }
        findViewById<TextView>(R.id.delete).setOnClickListener { delete() }
        findViewById<TextView>(R.id.rv).setOnClickListener { calculate() }

    }

    private fun setBNumbers() {
        val Bnumbers = listOf(
            R.id.b0, R.id.b1, R.id.b2,
            R.id.b3, R.id.b4, R.id.b5,
            R.id.b6, R.id.b7, R.id.b8,
            R.id.b9
        )

        Bnumbers.forEach { buttonId ->
            findViewById<TextView>(buttonId).setOnClickListener {
                appendN((it as TextView).text.toString())
            }
        }
    }

    private fun appendN(number: String) {
        if (NewOperation) {
            currentN = ""
            NewOperation = false
        }
        currentN += number
        updateDisplay()
    }



    private fun setOperation(operation: String) {
        OneN = currentN.toDouble()
        currentOp = operation
        currentN = ""
        updateDisplay()
    }

    private fun calculate() {
        if (currentOp.isEmpty() || currentN.isEmpty()) return

        val TwoN = currentN.toDouble()
        val result = when (currentOp) {
            "+" -> OneN + TwoN
            "-" -> OneN - TwoN
            "*" -> OneN * TwoN
            "/" -> if (TwoN != 0.0) OneN / TwoN else Double.NaN
            else -> Double.NaN
        }

        currentN = when {
            result.isNaN() -> "Error"
            result == result.toInt().toDouble() -> result.toInt().toString()
            else -> result.toString()
        }

        currentOp = ""
        NewOperation = true
        updateDisplay()
    }

    private fun delete() {
        currentN = ""
        OneN = 0.0
        currentOp = ""
        NewOperation = true
        Input.text = ""
        Output.text = ""
    }

    private fun updateDisplay() {
        Input.text = currentN.ifEmpty { "0" }
        Output.text = currentN
    }
}
