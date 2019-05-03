package me.ihint.datahelper.test.kotlin

import me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql.*
import me.ihint.datahelper.test.kotlin.impl.core.group.mysql.*
import me.ihint.datahelper.test.kotlin.impl.parser.mysql.ParserTest
import me.ihint.datahelper.test.kotlin.impl.compiler.mysql.*
import me.ihint.datahelper.test.kotlin.impl.helper.SQLHelperTest

fun main(args: Array<String>) {
    testDatatypeMysql()
    testGroupMysql()
    testCompilerMysql()
    ParserTest.test()
    SQLHelperTest.test()
}

fun testDatatypeMysql() {
    IntegerTest.test()
    DoubleTest.test()
    BooleanTest.test()
    VarcharTest.test()
    TextTest.test()
    TimestampTest.test()
}

fun testGroupMysql() {
    GroupTest.test()
}

fun testCompilerMysql() {
    InsertTest.test()
    UpdateTest.test()
    SelectTest.test()
    DeleteTest.test()
}
