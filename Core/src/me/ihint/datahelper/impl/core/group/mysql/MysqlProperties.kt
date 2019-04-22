package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Field

/**
 * MysqlProperties : interface
 *
 * Properties:
 *
 *      config["#table-name"] : String
 *   config["#not-null-list"] : List<Field>
 *      config["#order-list"] : List<Field>
 *
 */

interface MysqlProperties {
    fun getTableName(): String?
    fun setTableName(tableName: String)

    fun getNotNullList(): List<Field>?
    fun setNotNullList(list: List<Field>)

    fun getOrderList(): List<Field>?
    fun setOrderList(list: List<Field>)
}
