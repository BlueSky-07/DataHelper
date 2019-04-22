package me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.TEXT

object TextTest {
    fun test() {
        val config = SimpleConfig()
        val field = Field("fieldname", TEXT, config)

        var data = field.newData()
        assert(data.verify(false) == false)
        assert(data.verify(true) == true)
        try {
            TEXT.toEntry(data)
            throw Exception()
        } catch (e: Exception) {
            assert(e is ValueIsNullException)
        }

        data.value = "123"
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(TEXT.toEntry(data).value == "`123`")

        config.set("max", 5)
        data = field.newData("12345")
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)
        data.value = "1234"
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)

        config.set("min", 3)
        data = field.newData("12")
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)
        data.value = "123"
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)

        println("TEXT tests passed")
    }
}
