package me.ihint.datahelper.test.kotlin

import me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql.*
import me.ihint.datahelper.test.kotlin.impl.core.group.mysql.*

fun main(args: Array<String>) {
	testDatatypeMysql()
	testGroupMysql()
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
