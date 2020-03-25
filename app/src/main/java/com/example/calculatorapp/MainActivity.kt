package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    var expressionResult: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonOnClickListener()
    }

    private fun setButtonOnClickListener(){
        val numListenr = View.OnClickListener { v ->
            val b = (v as Button).text.toString()
            txt_operation.append(b)
            try {
                expressionResult = calculateResult()
                txt_result.text = expressionResult.toString()
            } catch (e: Exception) {
                Log.d("ErrorMessage", e.message)
            }
        }

        val opListenr = View.OnClickListener { v ->
            if (txt_operation.text.isNotEmpty()){
                val b = (v as Button).text.toString()
                txt_operation.append(b)
            }
        }

        btn_0.setOnClickListener (numListenr)
        btn_1.setOnClickListener (numListenr)
        btn_2.setOnClickListener (numListenr)
        btn_3.setOnClickListener (numListenr)
        btn_4.setOnClickListener (numListenr)
        btn_5.setOnClickListener (numListenr)
        btn_6.setOnClickListener (numListenr)
        btn_7.setOnClickListener (numListenr)
        btn_8.setOnClickListener (numListenr)
        btn_9.setOnClickListener (numListenr)

        btn_plus.setOnClickListener (opListenr)
        btn_sub.setOnClickListener (opListenr)
        btn_mul.setOnClickListener (opListenr)
        btn_div.setOnClickListener (opListenr)
        btn_point.setOnClickListener (opListenr)

        btn_percent.setOnClickListener {
            val op_string = txt_operation.text.toString()
            val lastNum = getNumbers(txt_operation.text.toString()).last()
            Log.d("ErrorMessage", "lastNum:" + lastNum)
            txt_operation.text = op_string.subSequence(0, op_string.length - lastNum.length)
            txt_operation.append((lastNum.toDouble()*0.01).toString())
            try {
                expressionResult = calculateResult()
                txt_result.text = expressionResult.toString()
            } catch (e: Exception) {
                Log.d("ErrorMessage", e.message)
            }
        }

        btn_sqrt.setOnClickListener {
            val op_string = txt_operation.text.toString()
            val lastNum = getNumbers(txt_operation.text.toString()).last()
            txt_operation.text = op_string.subSequence(0, op_string.length - lastNum.length)
            txt_operation.append(sqrt(lastNum.toDouble()).toString())
            try {
                expressionResult = calculateResult()
                txt_result.text = expressionResult.toString()
            } catch (e: Exception) {
                Log.d("ErrorMessage", e.message)
            }
        }

        btn_AC.setOnClickListener {
            txt_operation.text = ""
            txt_result.text = ""
        }

        btn_backSpack.setOnClickListener {
            val op_string = txt_operation.text.toString()
            if (op_string.isNotEmpty()) {
                txt_operation.text = op_string.subSequence(0, op_string.length - 1)
                txt_result.text = op_string.subSequence(0, op_string.length - 1)
            }
        }

        btn_equal.setOnClickListener {
            try {
                if (txt_result.text.isNotEmpty()) {
                    txt_operationHistory.append(txt_operation.text.toString() + "=" +txt_result.text.toString() + "\n")
                    txt_operation.text = ""
                }
            } catch (e: Exception) {
                Log.d("ErrorMessage", e.message)
            }
        }
    }

    private fun calculateResult(): Double {
        val expression = ExpressionBuilder(txt_operation.text.toString()).build()
        val result = expression.evaluate().toDouble()
        return result
    }

    private fun getNumbers(op:String): List<String> {
        val numList = op.split("\\+|-|\\*|/".toRegex())
        return numList
    }
}
