package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Field

/**
 * MysqlProperties : interface
 *
 * Properties:
 *
 *      config["tablename"] : String
 *        config["notnull"] : List<Field>
 *          config["order"] : List<Field>
 *
 */

interface MysqlProperties {
	fun getTableName(): String?
	fun setTableName(tablename: String)
	
	fun getNotNullList(): List<Field>?
	fun setNotNullList(list: List<Field>)
	
	fun getOrderList(): List<Field>?
	fun setOrderList(list: List<Field>)
}