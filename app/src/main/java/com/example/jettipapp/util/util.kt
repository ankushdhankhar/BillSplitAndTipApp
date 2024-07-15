package com.example.jettipapp.util

import androidx.compose.runtime.MutableState

fun calculateTotalTip (totalBill:Double, tipPercentage:Int): Double {
    return if (totalBill > 1 && totalBill.toString().isNotEmpty()) {
        (totalBill * tipPercentage) / 100
    } else 0.0

}
fun calculateTotalPerPerson(totalBill: Double, splitByState:Int ,tipPercentage: Int):Double{
    val bill= calculateTotalTip(totalBill=totalBill , tipPercentage=tipPercentage)+totalBill
    return (bill/splitByState)
}