package me.ihint.datahelper.test.kotlin.impl.compiler.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.compiler.mysql.SQLCompiler
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.*
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

object InsertTest {
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
        fields["integer"] = Field("integer", INTEGER, configOfInteger)

        val configOfBoolean = SimpleConfig()
        fields["boolean"] = Field("boolean", BOOLEAN, configOfBoolean)

        val configOfDouble = SimpleConfig()
        configOfDouble["min"] = 1.5
        configOfDouble["max"] = 2.5
        configOfDouble["fix"] = 3
        fields["double"] = Field("double", DOUBLE, configOfDouble)

        val configOfText = SimpleConfig()
        configOfText["max"] = 100
        configOfText["min"] = 5
        fields["text"] = Field("text", TEXT, configOfText)

        val configOfTimeStamp = SimpleConfig()
        configOfTimeStamp["input"] = FORMATTER_ISO
        configOfTimeStamp["output"] = FORMATTER_TIMESTAMP
        fields["timestamp"] = Field("timestamp", TIMESTAMP, configOfTimeStamp)

        struct = Struct(fields, SimpleConfig())
        struct.setTableName("test_table_name")
    }


    fun test() {
        testCase1()
        println("Insert tests passed")
    }

    fun testCase1() {
        val record: Record = struct.newRecord()
        record["varchar"] = "HelloKotlinLang1"
        record["integer"] = "7"
        record["boolean"] = "true"
        record["double"] = "1.87654321"
        record["text"] = "This is SQL Script Generator: \n\\'Hello World'"
        record["timestamp"] = "2011-12-03T10:15:30.000Z"
        println(SQLCompiler.insert(record))
    }
}
