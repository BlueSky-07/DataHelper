package me.ihint.datahelper.impl.compiler.mysql

import me.ihint.datahelper.compiler.SQLCompiler
import me.ihint.datahelper.core.Data
import me.ihint.datahelper.exception.compiler.InsertRequireValueException
import me.ihint.datahelper.exception.verify.ValueNotSetException
import me.ihint.datahelper.impl.core.group.mysql.Record
import java.lang.String.join

object SQLCompiler : SQLCompiler<Record> {
    override fun insert(record: Record): String {
        if (record.count(false) == 0) {
            throw ValueNotSetException()
        }
        record.verify(true) // for check
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
        val sqlBundle = produceSQLBundle(record)
        return "INSERT INTO `${
        record.getTableName()
        }` (${
        join(", ", sqlBundle.keyList)
        }) values (${
        join(", ", sqlBundle.valueList)
        });"
    }

    override fun insert(record: Record, returnId: Boolean): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(target: Record, condition: Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun select(condition: Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun select(condition: Record, offset: Long, size: Long): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(condition: Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private class SQLBundle {
        val keyList: ArrayList<String> = ArrayList()
        val valueList: ArrayList<String> = ArrayList()
        val equationList: ArrayList<String> = ArrayList()
    }

    private fun produceSQLBundle(record: Record, removeList: Set<String>?): SQLBundle {
        val dataList: List<Data> = ArrayList<Data>(record.bundle.values)
        val sqlBundle = SQLBundle()
        dataList.forEach { data ->
            run {
                when (data.value) {
                    null -> return@run
                    else -> {
                        val entry = data.toEntry()
                        val key = entry.key
                        val value = entry.value
                        if (removeList != null && removeList.contains(key)) return@run
                        sqlBundle.keyList.add(key)
                        sqlBundle.valueList.add(value)
                        sqlBundle.equationList.add("$key=$value")
                    }
                }
            }
        }
        return sqlBundle
    }

    private fun produceSQLBundle(record: Record): SQLBundle = produceSQLBundle(record, null)
}
