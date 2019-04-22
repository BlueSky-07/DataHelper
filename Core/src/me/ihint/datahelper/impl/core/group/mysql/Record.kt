package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.core.Group
import me.ihint.datahelper.exception.bundle.FieldNotFoundException
import me.ihint.datahelper.exception.config.ItemNotValidException
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

    fun clear() {
        bundle.forEach { (_, data) ->
            run {
                data.clear()
            }

        }
    }

    fun clear(fieldName: String) = when (val data = bundle[fieldName]) {
        null -> throw FieldNotFoundException()
        else -> data.clear()
    }

    fun count(allowNull: Boolean): Int = when (allowNull) {
        true -> bundle.size
        false -> {
            var count = 0
            bundle.forEach { (_, data) ->
                run {
                    count += when (data.value) {
                        null -> 0
                        else -> 1
                    }
                }
            }
            count
        }
    }
}
