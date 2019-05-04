package me.ihint.datahelper.impl.core.group.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.core.Group
import me.ihint.datahelper.exception.bundle.FieldNotFoundException
import me.ihint.datahelper.core.Bundle
import me.ihint.datahelper.core.Config

/**
 * Record(Group<Data>)
 *
 * one record of a table
 */

class Record(
        override val bundle: Bundle<Data>,
        override val config: Config,
        private val struct: Struct
) : Group<Data> {
    constructor(record: Record, clear: Boolean) : this(
            record.struct.newRecord().bundle,
            record.struct.config,
            record.struct
    ) {
        if (!clear) {
            record.bundle.forEach { (fieldName, data) ->
                run {
                    if (data.value != null)
                        this[fieldName] = data.value as String
                }
            }
        }
    }

    constructor(record: Record) : this(record, false)

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

    fun getTableName(): String? = struct.getTableName()

    fun getRequiredList(): List<Field>? = struct.getRequiredList()

    fun getOrderList(): List<Field>? = struct.getOrderList()

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

    fun verify(allowNull: Boolean): Boolean {
        var result = true
        bundle.forEach { (_, data) ->
            run {
                result = result && data.verify(allowNull)
            }
        }
        return result
    }

    fun verify(fieldName: String, allowNull: Boolean): Boolean {
        return bundle[fieldName]!!.verify(allowNull)
    }

    override fun toString(): String {
        return bundle.values.toString() + ";" + config.toString()
    }
}
