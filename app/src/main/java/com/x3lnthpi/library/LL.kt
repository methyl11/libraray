package com.x3lnthpi.library

import android.content.ContextWrapper
import androidx.lifecycle.ViewModel
import kotlin.collections.mutableListOf

val familyMembers = listOf("Rahul", "Kajal", "Anu", "Ajay", "Deepti")
val name: String = "Rahul"

class A(age:Int) {
    var age = age
    init {
        println("init block of Class A")
    }
}






fun main(){
    val doSomething: ()-> String
    val ds = {a: Int, b: Int -> (a*b)}(3,5)
    println(ds)
    val dd = {->"Rahul"}()
    println(dd)
    val x = familyMembers.get(2)
    println(x)
    val a = A(7)
    val b = A(8)
    val c = A(9)
    val clasMem = mutableListOf<A>(a,b,c)
}


class MyViewModel : ViewModel() {

}
