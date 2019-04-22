package me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql

import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.INTEGER

object IntegerTest {
    fun test() {
        var config = SimpleConfig()
        var field = Field("fieldname", INTEGER, config)

        var data = field.newData()
        assert(data.verify(false) == false)
        assert(data.verify(true) == true)
        try {
            INTEGER.toEntry(data)
            throw Exception()
        } catch (e: Exception) {
            assert(e is ValueIsNullException)
        }

        data.value = "abc"
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)

        config.set("max", 123)
        data = field.newData("123")
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)
        data.value = "122"
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(INTEGER.toEntry(data).value == "122")

        config = SimpleConfig()
        field = Field("fieldname", INTEGER, config)
        config.set("min", 123)
        data = field.newData("122")
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)
        data.value = "123"
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(INTEGER.toEntry(data).value == "123")

        config = SimpleConfig()
        field = Field("fieldname", INTEGER, config)
        config.set("min", 123)
        config.set("max", 124)
        data = field.newData("123")
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(INTEGER.toEntry(data).value == "123")
        data.value = "124"
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)

        println("INTEGER tests passed")
    }
}
