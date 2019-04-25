package me.ihint.datahelper.test.me.ihint.datahelper.test.kotlin.impl.compiler.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.compiler.mysql.SQLCompiler
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.BOOLEAN
import me.ihint.datahelper.impl.core.datatype.mysql.DOUBLE
import me.ihint.datahelper.impl.core.datatype.mysql.INTEGER
import me.ihint.datahelper.impl.core.datatype.mysql.VARCHAR
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct
import java.util.regex.Pattern

object InsertTest {
    private var struct: Struct
    init {
        val fields = SimpleBundle<Field>()
        fields["id"] = Field("id", INTEGER, SimpleConfig())

        val configOfVarchar = SimpleConfig()
        configOfVarchar["length"] = 16
        configOfVarchar["pattern"] = Pattern.compile("^\\w*\\d$")
        fields["varchar"] = Field("varchar", VARCHAR, configOfVarchar)

        val configOfInteger = SimpleConfig()
        configOfInteger["min"] = 0
        configOfInteger["max"] = 10
        fields["integer"] = Field("integer", INTEGER, configOfInteger)

        val configOfBoolean = SimpleConfig()
        fields["boolean"] = Field("boolean", BOOLEAN, configOfBoolean)

        val configOfDouble = SimpleConfig()
        configOfDouble["min"] = 1.5
        configOfDouble["max"] = 2.5
        configOfDouble["fix"] = 3
        fields["double"] = Field("double", DOUBLE, configOfDouble)

        val config = SimpleConfig()
        config.set("writable", true)

        struct = Struct(fields, config)
        struct.setTableName("test_table_name")
    }


    fun test() {
        testCase1()
        println("Insert tests passed")
    }

    fun testCase1() {
        val record: Record = struct.newRecord()
        record["boolean"] = "true"
        record["varchar"] = "HelloKotlinLang1"
        println(SQLCompiler.insert(record))

    }
}
