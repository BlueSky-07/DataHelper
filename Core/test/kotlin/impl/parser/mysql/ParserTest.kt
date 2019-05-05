package me.ihint.datahelper.test.kotlin.impl.parser.mysql

import me.ihint.datahelper.impl.parser.mysql.SQLParser
import java.time.format.DateTimeFormatter

object ParserTest {
    fun test() {
        val tables = SQLParser()
                .parseFromFile(
                        "classpath:tables.txt",
                        "utf8",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                )
        tables.forEach {
            run {
                println(it)
            }
        }
    }
}
