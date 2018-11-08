package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.core.Group
import me.ihint.datahelper.exception.FieldNotFoundException
import me.ihint.datahelper.exception.ItemNotValidException
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig

/**
 * Record(Group<Data>)
 *
 * one record of a table
 */

class Record(
		override val bundle: SimpleBundle<Data>,
		override val config: SimpleConfig
) : Group<Data>, MysqlProperties {
	operator fun get(fieldName: String): String? = when (bundle[fieldName]) {
		null -> throw FieldNotFoundException()
		else -> bundle[fieldName]!!.value
	}
	
	operator fun set(fieldName: String, value: String) {
		when (bundle[fieldName]) {
			null -> throw FieldNotFoundException()
			else -> bundle[fieldName]!!.value = value
		}
	}
	
	override fun getTableName(): String? = when (val tablename: Any? = config["tablename"]) {
		is String? -> tablename
		else -> throw ItemNotValidException()
	}
	
	override fun setTableName(tablename: String) {
		config["tablename"] = tablename
	}
	
	override fun getNotNullList(): List<Field>? = when (val notnull: Any? = config["notnull"]) {
		is List<*>? -> notnull as List<Field>?
		else -> throw ItemNotValidException()
	}
	
	override fun setNotNullList(list: List<Field>) {
		config["notnull"] = list
	}
	
	override fun getOrderList(): List<Field>? = when (val order: Any? = config["order"]) {
		is List<*>? -> order as List<Field>?
		else -> throw ItemNotValidException()
	}
	
	override fun setOrderList(list: List<Field>) {
		config["order"] = list
	}
}