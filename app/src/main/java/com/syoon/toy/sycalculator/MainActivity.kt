package com.syoon.toy.sycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.syoon.toy.sycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = true

    private val operatorList = listOf<Char>('+', '-', '×', '÷')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Number listeners
        binding.btnZero.setOnClickListener {
            appendNumberClick("0")
            lastNumeric = true
            lastDot = false
        }
        binding.btnOne.setOnClickListener {
            appendNumberClick("1")
            lastNumeric = true
            lastDot = false
        }
        binding.btnTwo.setOnClickListener {
            appendNumberClick("2")
            lastNumeric = true
            lastDot = false
        }
        binding.btnThree.setOnClickListener {
            appendNumberClick("3")
            lastNumeric = true
            lastDot = false
        }
        binding.btnFour.setOnClickListener {
            appendNumberClick("4")
            lastNumeric = true
            lastDot = false
        }
        binding.btnFive.setOnClickListener {
            appendNumberClick("5")
            lastNumeric = true
            lastDot = false
        }
        binding.btnSix.setOnClickListener {
            appendNumberClick("6")
            lastNumeric = true
            lastDot = false
        }
        binding.btnSeven.setOnClickListener {
            appendNumberClick("7")
            lastNumeric = true
            lastDot = false
        }
        binding.btnEight.setOnClickListener {
            appendNumberClick("8")
            lastNumeric = true
            lastDot = false
        }
        binding.btnNine.setOnClickListener {
            appendNumberClick("9")
            lastNumeric = true
            lastDot = false
        }

        //Operator listener
        binding.btnPlus.setOnClickListener {
            appendOperator( "+")
        }
        binding.btnSubtract.setOnClickListener {
            appendOperator( "-")
        }
        binding.btnMultiply.setOnClickListener {
            appendOperator( "×")
        }
        binding.btnDivide.setOnClickListener {
            appendOperator("÷")
        }

        //Percent listener
//        binding.btnPercent.setOnClickListener {
//            appendPercent("%")
//        }

        //Clear listener
        binding.btnClr.setOnClickListener {
            clear()
        }

        binding.btnDot.setOnClickListener {
            appendDecimalPoint(".")
        }

        //Equal listener
        binding.btnEqual.setOnClickListener {
            onEqual()
        }


        //BackSpace listener
        binding.btnBack.setOnClickListener {
            var text = binding.tvExpression.text
            if (text.isNotEmpty()) {
                binding.tvExpression.text = text.substring(0, text.length - 1)
                binding.tvPreview.text = ""
            }
        }
    }

    //number button click
    private fun appendNumberClick(string: String) {

        val expressionText = binding.tvExpression.text

        if (expressionText.isNotEmpty() && expressionText.length >= 15) {
            Toast.makeText(this, "15자리까지 입력할 수 있어요", Toast.LENGTH_SHORT).show()
            return
        }

        binding.tvExpression.append(string)
        binding.tvPreview.text = ""
    }

    //operator button click
    private fun appendOperator(string: String) {
        var tvValue = binding.tvExpression.text
        if (tvValue.toString() == "") {
            Toast.makeText(this, "완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
        } else if (tvValue.substring(tvValue.length - 1, tvValue.length) == string) {
            return
        } else if (operatorList.contains(tvValue.toString().last())) {
            binding.tvExpression.text = tvValue.substring(0, tvValue.length - 1)
            binding.tvExpression.append(string)
        } else {
            binding.tvExpression.append(string)
        }
    }

//    private fun appendPercent(string: String) {
//        var tvValue = binding.tvExpression.text
//        if (tvValue.toString() == "") {
//            return
//        } else if (operatorList.contains(tvValue.toString().last())) {
//            return
//        } else if (tvValue.substring(tvValue.length - 1, tvValue.length) == string){
//            return
//        } else {
//            binding.tvExpression.append(string)
//        }
//    }

    //clr button click
    private fun clear() {
        binding.tvExpression.text = ""
        binding.tvPreview.text = ""
    }

    // dot button click
    private fun appendDecimalPoint(string: String) {
        var tvValue = binding.tvExpression.text
        if (tvValue.toString() == "") {
            return
        } else if (operatorList.contains(tvValue.toString().last())) {
            return
        }  else if(lastNumeric && !lastDot) {
            binding.tvExpression.append(string)
            lastNumeric = false
            lastDot = true
        }
    }

    //equal button click - 수식 고려
    private fun onEqual() {
        if (lastNumeric) {
            var tvValue = binding.tvExpression.text.toString()
            var prefix = ""
            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }

                when {
                    tvValue.contains("÷") -> {
                        val splitedValue = tvValue.split("÷")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        if (two == "0") {
                            Toast.makeText(this, "0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            binding.tvExpression.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                        }
                    }
                    tvValue.contains("×") -> {

                        val splitedValue = tvValue.split("×")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvExpression.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("-") -> {

                        val splitedValue = tvValue.split("-")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvExpression.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {

                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvExpression.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
        // 연산 결과가 15자리 이상일 경우
        // Toast.makeText(this, "15자리까지만 계산 가능합니다.", Toast.LENGTH_SHORT).show()
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}