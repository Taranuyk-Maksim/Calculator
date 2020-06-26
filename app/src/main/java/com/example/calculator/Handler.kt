package com.example.calculator

import android.util.Log
import android.view.View
import androidx.databinding.ObservableDouble

class Handler {

    val exp = Expression()
    val pln = PolishNotation()
    var rez = ObservableDouble()

    //Click handler
    fun btnClick(view: View){
        when(view.id){
            R.id.btn_zero -> exp.setValue("0")
            R.id.btn_one -> exp.setValue("1")
            R.id.btn_two -> exp.setValue("2")
            R.id.btn_three -> exp.setValue("3")
            R.id.btn_four -> exp.setValue("4")
            R.id.btn_five -> exp.setValue("5")
            R.id.btn_six -> exp.setValue("6")
            R.id.btn_seven -> exp.setValue("7")
            R.id.btn_eight -> exp.setValue("8")
            R.id.btn_nine -> exp.setValue("9")

            R.id.btn_open_bracket -> exp.setValue("(")
            R.id.btn_close_bracket -> exp.setValue(")")

            R.id.btn_point -> exp.setValue(".")

            R.id.btn_plus -> exp.setValue("+")
            R.id.btn_minus -> exp.setValue("-")

            R.id.btn_multiplication -> exp.setValue("*")
            R.id.btn_division -> exp.setValue("/")

            R.id.btn_percent -> exp.setValue("%")

            R.id.btn_clean -> {
                exp.cleanAll()
                rez.set(0.0)
            }
            R.id.btn_delete -> {
                exp.delete()
            }
            R.id.btn_equals ->{
                rez.set(pln.calculator(exp.getListExpression()))
                exp.getListExpression().clear()
            }
        }
    }
}