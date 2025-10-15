package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var input: TextView
    private var expr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)

        findViewById<TextView>(R.id.b0).setOnClickListener { add("0") }
        findViewById<TextView>(R.id.b1).setOnClickListener { add("1") }
        findViewById<TextView>(R.id.b2).setOnClickListener { add("2") }
        findViewById<TextView>(R.id.b3).setOnClickListener { add("3") }
        findViewById<TextView>(R.id.b4).setOnClickListener { add("4") }
        findViewById<TextView>(R.id.b5).setOnClickListener { add("5") }
        findViewById<TextView>(R.id.b6).setOnClickListener { add("6") }
        findViewById<TextView>(R.id.b7).setOnClickListener { add("7") }
        findViewById<TextView>(R.id.b8).setOnClickListener { add("8") }
        findViewById<TextView>(R.id.b9).setOnClickListener { add("9") }

        findViewById<TextView>(R.id.bp).setOnClickListener { add("+") }
        findViewById<TextView>(R.id.bm).setOnClickListener { add("-") }
        findViewById<TextView>(R.id.bmn).setOnClickListener { add("*") }
        findViewById<TextView>(R.id.del).setOnClickListener { add("/") }

        findViewById<TextView>(R.id.rv).setOnClickListener { calc() }
        findViewById<TextView>(R.id.delete).setOnClickListener { clear() }
    }

    private fun add(s: String) {
        expr += s
        input.text = expr
    }

    private fun calc() {
        val res = calcex(expr)
        expr = res.toString()
        input.text = expr
    }

    private fun clear() {
        expr = ""
        input.text = ""
    }

    private fun calcex(s: String): Int {
        var n1 = 0
        var n2 = 0
        var op = " "

        for (i in s.indices) {
            if (s[i] in "+-*/"){
                op = s[i].toString()
                n1 = s.substring(0, i).toInt()
                n2 = s.substring(i+1).toInt()
                break
            }
        }

            return when (op) {
                "+" -> n1 + n2
                "-" -> n1 - n2
                "*" -> n1 * n2
                "/" -> n1 / n2
                else -> 0
            }
    }
}