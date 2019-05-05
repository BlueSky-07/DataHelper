package me.ihint.datahelper.test.kotlin.impl.helper

import me.ihint.datahelper.annotation.DateTime
import me.ihint.datahelper.annotation.Lang
import me.ihint.datahelper.annotation.File
import me.ihint.datahelper.impl.helper.SQLHelper

object SQLHelperTest {
    class Test {
        @File("classpath:tables.txt")
        @DateTime
        @Lang
        val helper = SQLHelper()
    }

    fun test() {
        val test = Test()
        test.helper.init(test, "helper")
        println("teachers:" + test.helper["teachers"])
        println("students:" + test.helper["students"])
        println("not-found:" + test.helper["not-found"])
    }
}


