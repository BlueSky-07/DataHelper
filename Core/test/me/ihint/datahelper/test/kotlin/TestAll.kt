package me.ihint.datahelper.test.kotlin

import me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql.*

fun main(args: Array<String>) {
	testDatatypeMysql()
}

fun testDatatypeMysql() {
	IntegerTest.test()
	DoubleTest.test()
	BooleanTest.test()
	VarcharTest.test()
	TextTest.test()
	TimestampTest.test()
}
