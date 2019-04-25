package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.core.Group
import me.ihint.datahelper.exception.config.ItemNotValidException
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig

/**
 * Struct(Group<Field>)
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
    }, SimpleConfig(config))

    override fun getTableName(): String? = when (val tableName: Any? = config["#table-name"]) {
        is String? -> tableName
        else -> throw ItemNotValidException()
    }

    override fun setTableName(tableName: String) {
        config["#table-name"] = tableName
    }

    @Suppress("UNCHECKED_CAST")
    override fun getNotNullList(): List<Field>? = when (val notNullList: Any? = config["#not-null-list"]) {
        is List<*>? -> notNullList as List<Field>?
        else -> throw ItemNotValidException()
    }

    override fun setNotNullList(list: List<Field>) {
        config["#not-null-list"] = list
    }

    @Suppress("UNCHECKED_CAST")
    override fun getOrderList(): List<Field>? = when (val orderList: Any? = config["#order-list"]) {
        is List<*>? -> orderList as List<Field>?
        else -> throw ItemNotValidException()
    }

    override fun setOrderList(list: List<Field>) {
        config["#order-list"] = list
    }
}
