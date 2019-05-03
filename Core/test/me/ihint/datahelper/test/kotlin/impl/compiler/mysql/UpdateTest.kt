package me.ihint.datahelper.test.me.ihint.datahelper.test.kotlin.impl.compiler.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.compiler.mysql.SQLCompiler
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.*
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

object UpdateTest {
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
        configOfTimeStamp["read"] = FORMATTER_ISO
        configOfTimeStamp["write"] = FORMATTER_TIMESTAMP
        fields["timestamp"] = Field("timestamp", TIMESTAMP, configOfTimeStamp)

        struct = Struct(fields, SimpleConfig())
        struct.setTableName("test_table_name")
    }


    fun test() {
        testCase1()
        println("Update tests passed")
    }

    fun testCase1() {
        val target: Record = struct.newRecord()
        target["varchar"] = "HelloKotlinLang1"
        target["integer"] = "7"
        target["boolean"] = "true"
        target["double"] = "1.87654321"
        target["text"] = "This is SQL Script Generator: \n\\'Hello World'"
        target["timestamp"] = "2011-12-03T10:15:30.000Z"
        val condition: Record = struct.newRecord()
        condition["varchar"] = "HelloKotlinLang1"
        condition["integer"] = "7"
        condition["boolean"] = "true"
        condition["double"] = "1.87654321"
        condition["text"] = "This is SQL Script Generator: \n\\'Hello World'"
        condition["timestamp"] = "2011-12-03T10:15:30.000Z"
        println(SQLCompiler.update(target, condition))
    }
}
