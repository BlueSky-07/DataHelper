package me.ihint.datahelper.test.kotlin.impl.parser.mysql

import me.ihint.datahelper.impl.parser.mysql.SQLParser

object ParserTest{
    fun test () {
        val tables = SQLParser("default").parseFromFile("/Users/bluesky/workspace/DataHelper/Core/resources/tables.txt", "utf-8")
        tables.forEach {
            run {
                println(it)
            }
        }
    }
}
