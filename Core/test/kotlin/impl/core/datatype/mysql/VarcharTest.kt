package me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql

import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.VARCHAR

import java.util.regex.Pattern

object VarcharTest {
    fun test() {
        var config = SimpleConfig()
        config.set("length", 5)
        var field = Field("fieldname", VARCHAR, config)

        var data = field.newData()
        assert(data.verify(false) == false)
        assert(data.verify(true) == true)
        try {
            VARCHAR.toEntry(data)
            throw Exception()
        } catch (e: Exception) {
            assert(e is ValueIsNullException)
        }

        data.value = "123"
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(VARCHAR.toEntry(data).value == "'123'")

        data.value = "123456"
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)

        config.set("pattern", Pattern.compile("[\\s]+"))
        data = field.newData("123")
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)

        config = SimpleConfig()
        config.set("length", 5)
        config.set("pattern", Pattern.compile("[\\d]+"))
        field = Field("fieldname", VARCHAR, config)
        data = field.newData("123")
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(VARCHAR.toEntry(data).value == "'123'")

        data = Field("fieldname", VARCHAR, SimpleConfig()).newData()
        data.value = "123"
        try {
            data.verify(true)
            throw Exception()
        } catch (e: Exception) {
            assert(e is TypeCastException)
        }

        println("VARCHAR tests passed")
    }
}
