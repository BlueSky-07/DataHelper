package me.ihint.datahelper.test.kotlin.impl.core.group.mysql

import me.ihint.datahelper.core.Field
import me.ihint.datahelper.exception.FieldNotFoundException
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.INTEGER
import me.ihint.datahelper.impl.core.datatype.mysql.VARCHAR
import me.ihint.datahelper.impl.core.group.mysql.Struct

object GroupTest {
	fun test() {
		val fields = SimpleBundle<Field>()
		fields["id"] = Field("id", INTEGER, SimpleConfig())
		
		val configOfVarchar = SimpleConfig()
		configOfVarchar.set("length", 15)
		fields["username"] = Field("id", VARCHAR, configOfVarchar)
		
		val configOfAge = SimpleConfig()
		configOfAge.set("min", 0)
		fields["age"] = Field("age", INTEGER, configOfAge)
		
		val config = SimpleConfig()
		config.set("writable", true)
		
		val struct = Struct(fields, config)
		struct.setTableName("test_table_name")
		
		val record = struct.newRecord()
		
		assert(record.getTableName() == "test_table_name")
		assert(record.config["writable"] as Boolean == true)
		
		val id = record.bundle["id"]
		id!!.value = "0"
		assert(record.get("id") == "0")
		
		record["username"] = "BlueSky"
		assert(record.get("username") == "BlueSky")
		
		record.bundle["age"]!!.value = "20"
		assert(record.get("age") == "20")
		
		try {
			record.get("null")
			throw Exception()
		} catch (e: Exception) {
			assert(e is FieldNotFoundException)
		}
		
		try {
			record["null"] = "null"
			throw Exception()
		} catch (e: Exception) {
			assert(e is FieldNotFoundException)
		}
		
		println("Group tests passed")
	}
}
