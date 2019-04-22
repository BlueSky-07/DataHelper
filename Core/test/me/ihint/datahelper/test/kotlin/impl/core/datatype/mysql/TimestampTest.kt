package me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql

import me.ihint.datahelper.core.Config
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.TIMESTAMP

import java.time.format.DateTimeFormatter

object TimestampTest {
    private val FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private val FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    fun test() {
        var config: Config = SimpleConfig()
        var field = Field("fieldname", TIMESTAMP, config)

        var data = field.newData()
        assert(data.verify(false) == false)
        assert(data.verify(true) == true)
        try {
            TIMESTAMP.toEntry(data)
            throw Exception()
        } catch (e: Exception) {
            assert(e is ValueIsNullException)
        }

        config.set("read", FORMATTER_ISO)
        config.set("write", FORMATTER_TIMESTAMP)
        data = field.newData("2011-12-03T10:15:30.000Z")
        assert(data.verify(false) == true)
        assert(data.verify(true) == true)
        assert(TIMESTAMP.toEntry(data).value == "`2011/12/03 10:15:30`")

        data.value = "2011/12/03 10:15:30"
        assert(data.verify(false) == false)
        assert(data.verify(true) == false)

        config = SimpleConfig()
        field = Field("fieldname", TIMESTAMP, config)
        data = field.newData("2011-12-03T10:15:30.000Z")
        try {
            data.verify(false)
            throw Exception()
        } catch (e: Exception) {
            assert(e is TypeCastException)
        }

        println("TIMESTAMP tests passed")
    }
}
