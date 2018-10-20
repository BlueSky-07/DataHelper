package me.ihint.datahelper.test.kotlin.impl.core.datatype.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.BOOLEAN

object BooleanTest {
	fun test() {
		val field = Field("fieldname", BOOLEAN, SimpleConfig())
		
		val data = field.newData()
		assert(data.verify(false) == false)
		assert(data.verify(true) == true)
		try {
			BOOLEAN.toEntry(data)
			throw Exception()
		} catch (e: Exception) {
			assert(e is ValueIsNullException)
		}
		
		data.value = "abc"
		assert(data.verify(false) == false)
		assert(data.verify(true) == false)
		
		data.value = "1"
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(BOOLEAN.toEntry(data).value == "TRUE")
		
		data.value = "True"
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(BOOLEAN.toEntry(data).value == "TRUE")
		
		data.value = "0"
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(BOOLEAN.toEntry(data).value == "FALSE")
		
		data.value = "False"
		assert(data.verify(false) == true)
		assert(data.verify(true) == true)
		assert(BOOLEAN.toEntry(data).value == "FALSE")
		
		println("BOOLEAN tests passed")
	}
}
