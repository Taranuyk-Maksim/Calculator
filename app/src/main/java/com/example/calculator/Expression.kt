package com.example.calculator

import android.util.Log
import androidx.databinding.ObservableField
import java.util.*
import kotlin.collections.ArrayList

class Expression {

    private var expression = ArrayList<String>()
    private var strExpression = StringBuilder()
    private var obsExpression = ObservableField<String>()

    //Gets characters to convert them to StringBuilder
    fun setValue(str : String) {
        strExpression.append(str)
        obsExpression.set(strExpression.toString())
    }

    //Deletes the last item
    fun delete(){
        if(!obsExpression.get().isNullOrEmpty()){
            obsExpression.set(strExpression.deleteCharAt(strExpression.lastIndex).toString())
        }
    }
    //Clean all items
    fun cleanAll(){
        expression.clear()
        strExpression.clear()
        obsExpression.set("")
    }

    /**
     * We need to split the string
     * into tokens for counting.
     */
    //Converts a String to ArrayList
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
    }

    fun getListExpression() : ArrayList<String>{
        parser(strExpression.toString())
        return expression
    }

    fun getExpression() : ObservableField<String>{
        return obsExpression
    }

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
}
