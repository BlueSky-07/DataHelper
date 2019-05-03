package me.ihint.datahelper.impl.compiler.mysql

import me.ihint.datahelper.compiler.SQLCompiler
import me.ihint.datahelper.core.Data
import me.ihint.datahelper.exception.compiler.InsertRequireValueException
import me.ihint.datahelper.exception.compiler.RecordIsEmptyException
import me.ihint.datahelper.exception.compiler.SelectLimitNotValidException
import me.ihint.datahelper.impl.core.group.mysql.Record
import java.lang.String.join

object SQLCompiler : SQLCompiler<Record> {
    override fun insert(record: Record): String {
        if (record.count(false) == 0) throw RecordIsEmptyException()
        record.verify(true)
        val notNullList = record.getNotNullList()
        if (notNullList != null && notNullList.isNotEmpty()) {
            notNullList.forEach { field ->
                run {
                    val fieldName = field.name
                    when (record.verify(fieldName, false)) {
                        true -> return@run
                        else -> throw InsertRequireValueException()
                    }
                }
            }
        }
        val dataSet = newSQLDataSet(record)
        return "INSERT INTO `${
        record.getTableName()
        }` (${
        join(", ", dataSet.keyList)
        }) VALUES (${
        join(", ", dataSet.valueList)
        });"
    }

    override fun insert(record: Record, returnId: Boolean): String =
            insert(record) + when (returnId) {
                true -> " SELECT LAST_INSERT_ID() as `id`;"
                false -> ""
            }

    override fun update(target: Record, condition: Record): String {
        if (target.count(false) == 0 || condition.count(false) == 0)
            throw RecordIsEmptyException()
        target.verify(true)
        condition.verify(true)
        val targetDataSet = newSQLDataSet(target)
        val conditionDataSet = newSQLDataSet(condition)
        return "UPDATE `${
        condition.getTableName()
        }` SET ${
        join(", ", targetDataSet.equationList)
        } WHERE TRUE ${
        join(" ", conditionDataSet.equationList.map { equation -> "AND $equation" })
        };"
    }

    override fun select(condition: Record): String {
        val orderList = condition.getOrderList()
        condition.verify(true)
        val dataSet = newSQLDataSet(condition)
        return "SELECT * FROM `${
        condition.getTableName()
        }` WHERE TRUE${
        if (condition.count(false) > 0)
            " ${join(" ", dataSet.equationList.map { equation -> "AND $equation" })}"
        else ""
        }${
        if (orderList != null && orderList.isNotEmpty())
            " ORDER BY ${join(", ", orderList.map { field -> "`${field.name}` ${field.config["order"]}" })}"
        else ""
        };"
    }

    override fun select(condition: Record, offset: Long, size: Long): String {
        if (offset < 0 || size < 0) throw SelectLimitNotValidException()
        val sql = select(condition)
        return "${
        sql.substring(0, sql.lastIndex)
        } LIMIT $offset, $size;"
    }

    override fun delete(condition: Record): String {
        if (condition.count(false) == 0) throw RecordIsEmptyException()
        condition.verify(true)
        val dataSet = newSQLDataSet(condition)
        return "DELETE FROM `${
        condition.getTableName()
        }` WHERE TRUE ${
        join(" ", dataSet.equationList.map { equation -> "AND $equation" })
        };"
    }

    private class SQLDataSet {
        val keyList: ArrayList<String> = ArrayList()
        val valueList: ArrayList<String> = ArrayList()
        val equationList: ArrayList<String> = ArrayList()
    }

    private fun newSQLDataSet(record: Record): SQLDataSet {
        val dataList: List<Data> = ArrayList<Data>(record.bundle.values)
        val dataSet = SQLDataSet()
        dataList.forEach { data ->
            run {
                when (data.value) {
                    null -> return@run
                    else -> {
                        val entry = data.toEntry()
                        val key = entry.key
                        val value = entry.value
                        dataSet.keyList.add(key)
                        dataSet.valueList.add(value)
                        dataSet.equationList.add("$key=$value")
                    }
                }
            }
        }
        return dataSet
    }
}
