package me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.DOUBLE

object DoubleTest {
	fun test() {
		var config = SimpleConfig()
		var field = Field("fieldname", DOUBLE, config)
		
		var data = field.newData()
		assert(data.verify(false) == false)
		assert(data.verify(true) == true)
		try {
			DOUBLE.toEntry(data)
			throw Exception()
		} catch (e: Exception) {
			assert(e is ValueIsNullException)
		}
		
		data.value = "abc"
		assert(data.verify(false) == false)
		assert(data.verify(true) == false)
		
		config["max"] = 1.23
		data = field.newData("1.23")
		assert(data.verify(false) == false)
		assert(data.verify(true) == false)
		data.value = "1.22"
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(DOUBLE.toEntry(data).value == "1.22")
		
		
		config = SimpleConfig()
		config.set("min", 1.23)
		field = Field("fieldname", DOUBLE, config)
		data = field.newData("1.22")
		assert(data.verify(false) == false)
		assert(data.verify(true) == false)
		data.value = "1.23"
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(DOUBLE.toEntry(data).value == "1.23")
		
		config = SimpleConfig()
		config.set("max", 1.24)
		config.set("min", 1.23)
		field = Field("fieldname", DOUBLE, config)
		data = field.newData("1.23")
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(DOUBLE.toEntry(data).value == "1.23")
		data.value = "1.24"
		assert(data.verify(false) == false)
		assert(data.verify(true) == false)
		
		config["fix"] = 2
		data = field.newData("1.234")
		assert(DOUBLE.toEntry(data).value == "1.23")
		data.value = "1.235"
		assert(DOUBLE.toEntry(data).value == "1.23")
		
		println("DOUBLE tests passed")
	}
}
