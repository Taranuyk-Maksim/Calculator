package com.example.calculator

import android.util.Log
import androidx.databinding.ObservableField
import java.util.*
import kotlin.collections.ArrayList

class Expression {

    private var expression = ArrayList<String>()
    private var strExpression = StringBuilder()
    private var obsExpression = ObservableField<String>()



    fun setValue(str : String) {
        strExpression.append(str)
        obsExpression.set(strExpression.toString())
    }

    private fun parser(str: String) {

        val buf = StringBuilder()
        val chars = str.toCharArray()

        for (i in 0..str.length) {
            try {
                val ch = chars[i].toString()
                if (isDigit(ch)) {
                    buf.append(ch)
                } else {
                    if (buf.isNotEmpty()) {
                        expression.add(buf.toString())
                        buf.setLength(0)
                    }
                    expression.add(ch)
                }
            } catch (ex: ArrayIndexOutOfBoundsException) {
                if (buf.isNotEmpty()) {
                    expression.add(buf.toString())
                }
            }
        }
        Log.v("list", strExpression.toString())
    }

    fun getListExpression() : ArrayList<String>{
        parser(strExpression.toString())
        return expression
    }

    fun getExpression() : ObservableField<String>{

        return obsExpression
    }
    @Throws(NumberFormatException::class)
    private fun isDigit(s: String): Boolean {
        return try {
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
