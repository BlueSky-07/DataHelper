package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.*
import me.ihint.datahelper.exception.config.ItemNotValidException
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig

/**
 * Struct(Group<Field>)
 *
 * one struct of a table
 */

class Struct(
        override val bundle: Bundle<Field>,
        override val config: Config
) : Group<Field> {
    fun newRecord() = Record(SimpleBundle<Data>().also {
        bundle.forEach { (fieldName, field) ->
            run {
                it[fieldName] = field.newData()
            }
        }
    }, SimpleConfig(config as SimpleConfig), this)

    fun getTableName(): String? = when (val tableName: Any? = config["#table-name"]) {
        is String? -> tableName
        else -> throw ItemNotValidException()
    }

    fun setTableName(tableName: String) {
        config["#table-name"] = tableName
    }

    @Suppress("UNCHECKED_CAST")
    fun getRequiredList(): List<Field>? = when (val requiredList: Any? = config["#required-list"]) {
        is List<*>? -> requiredList as List<Field>?
        else -> throw ItemNotValidException()
    }

    fun setRequiredList(list: List<Field>) {
        config["#required-list"] = list
    }

    @Suppress("UNCHECKED_CAST")
    fun getOrderList(): List<Field>? = when (val orderList: Any? = config["#order-list"]) {
        is List<*>? -> orderList as List<Field>?
        else -> throw ItemNotValidException()
    }

    fun setOrderList(list: List<Field>) {
        config["#order-list"] = list
    }

    override fun toString(): String {
        return bundle.values.toString() + ";" + config.toString()
    }
}
