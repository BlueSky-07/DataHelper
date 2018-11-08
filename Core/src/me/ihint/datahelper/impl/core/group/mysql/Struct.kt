package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.core.Group
import me.ihint.datahelper.exception.ItemNotFoundException
import me.ihint.datahelper.exception.ItemNotValidException
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig

/**
 * Struct(Group<Field, Any>)
 *
 * one struct of a table
 */

class Struct(
		override val bundle: SimpleBundle<Field>,
		override val config: SimpleConfig
) : Group<Field>, MysqlProperties {
	fun newRecord() = Record(SimpleBundle<Data>().also {
		bundle.forEach { (fieldName, field) ->
			run {
				it[fieldName] = field.newData()
			}
		}
	}, config)
	
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