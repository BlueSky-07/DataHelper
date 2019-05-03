package me.ihint.datahelper.test.me.ihint.datahelper.test.kotlin.impl.compiler.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.compiler.mysql.SQLCompiler
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.*
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

object SelectTest {
    private var struct: Struct
    private val FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private val FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    
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
        configOfInteger["order"] = "ASC"
        fields["integer"] = Field("integer", INTEGER, configOfInteger)

        val configOfBoolean = SimpleConfig()
        fields["boolean"] = Field("boolean", BOOLEAN, configOfBoolean)

        val configOfDouble = SimpleConfig()
        configOfDouble["min"] = 1.5
        configOfDouble["max"] = 2.5
        configOfDouble["fix"] = 3
        configOfDouble["order"] = "DESC"
        fields["double"] = Field("double", DOUBLE, configOfDouble)

        val configOfText = SimpleConfig()
        configOfText["max"] = 100
        configOfText["min"] = 5
        fields["text"] = Field("text", TEXT, configOfText)

        val configOfTimeStamp = SimpleConfig()
        configOfTimeStamp["read"] = FORMATTER_ISO
        configOfTimeStamp["write"] = FORMATTER_TIMESTAMP
        fields["timestamp"] = Field("timestamp", TIMESTAMP, configOfTimeStamp)

        struct = Struct(fields, SimpleConfig())
        struct.setTableName("test_table_name")
        @Suppress("UNCHECKED_CAST")
        struct.setOrderList(Arrays.asList(fields["integer"], fields["double"]) as List<Field>)
    }


    fun test() {
        testCase1()
        println("Select tests passed")
    }

    fun testCase1() {
        val condition: Record = struct.newRecord()
        condition["id"] = "123"
        println(SQLCompiler.select(condition))
        println(SQLCompiler.select(condition, 1, 10))
    }
}
