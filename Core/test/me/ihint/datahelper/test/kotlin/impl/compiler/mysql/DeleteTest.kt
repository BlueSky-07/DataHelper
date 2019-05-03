package me.ihint.datahelper.test.me.ihint.datahelper.test.kotlin.impl.compiler.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.compiler.mysql.SQLCompiler
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.*
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct

object DeleteTest {
    private var struct: Struct

    init {
        val fields = SimpleBundle<Field>()
        fields["id"] = Field("id", INTEGER, SimpleConfig())

        struct = Struct(fields, SimpleConfig())
        struct.setTableName("test_table_name")
    }


    fun test() {
        testCase1()
        println("Delete tests passed")
    }

    fun testCase1() {
        val condition: Record = struct.newRecord()
        condition["id"] = "123"
        println(SQLCompiler.delete(condition))
    }
}
