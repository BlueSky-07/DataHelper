package me.ihint.datahelper.impl.helper

import me.ihint.datahelper.DataHelper
import me.ihint.datahelper.annotation.DateTime
import me.ihint.datahelper.annotation.Lang
import me.ihint.datahelper.annotation.Path
import me.ihint.datahelper.compiler.SQLCompiler
import me.ihint.datahelper.parser.SQLParser
import me.ihint.datahelper.core.Bundle
import me.ihint.datahelper.exception.runtime.InitializationException
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct
import java.time.format.DateTimeFormatter

class SQLHelper : DataHelper<SQLCompiler<Record>, SQLParser<Struct>>() {
    // flag for avoid repeat settings
    private var pathSet = false
    private var dateTimeSet = false
    private var langSet = false

    // @Path
    private var file: String = ""
    private var charset: String = ""

    // @DateTime
    private var readDateTimeFormatter: DateTimeFormatter? = null
    private var writeDateTimeFormatter: DateTimeFormatter? = null

    // Struct List
    private var structList: Bundle<Struct>? = null

    // SQLCompiler & SQLParser
    override var compiler: SQLCompiler<Record>? = null
    override var parser: SQLParser<Struct>? = null

    operator fun get(tableName: String): Struct? = structList!![tableName]

    fun init(caller: Any, name: String) {
        val callerClass = caller.javaClass
        location = callerClass.name + "." + name
        if (isInited) {
            throw InitializationException("already initialized")
        }
        var instanceFoundInCaller = false
        val fields = callerClass.declaredFields
        for (field in fields) {
            if (field.name != name) {
                continue
            }
            if (field.type == SQLHelper::class.java) {
                instanceFoundInCaller = true
                field.isAccessible = true

                for (annotation in field.declaredAnnotations) {
                    when (annotation.annotationClass) {
                        Path::class -> {
                            if (pathSet) {
                                throw InitializationException("`@Path` has already been set")
                            }
                            val a = annotation as Path
                            file = a.file
                            charset = a.charset
                            pathSet = true
                        }
                        DateTime::class -> {
                            if (dateTimeSet) {
                                throw InitializationException("`@DateTime` has already been set")
                            }
                            val a = annotation as DateTime
                            readDateTimeFormatter = DateTimeFormatter.ofPattern(a.read)
                            writeDateTimeFormatter = DateTimeFormatter.ofPattern(a.write)
                            dateTimeSet = true
                        }
                        Lang::class -> {
                            if (langSet) {
                                throw InitializationException("`@Lang` has already been set")
                            }
                            val a = annotation as Lang
                            when (a.lang) {
                                "mysql" -> {
                                    compiler = me.ihint.datahelper.impl.compiler.mysql.SQLCompiler
                                    parser = me.ihint.datahelper.impl.parser.mysql.SQLParser()
                                }
                                else -> {
                                    throw InitializationException("${a.lang} has not been implemented yet")
                                }
                            }
                            langSet = true
                        }
                    }
                }
            } else {
                throw InitializationException("$location is not an instance of ${SQLHelper::class.java.name}")
            }
            break
        }
        if (!instanceFoundInCaller) {
            throw InitializationException("cannot find an instance of ${SQLHelper::class.java.name} called \"$location\"")
        }

        if (!pathSet)
            throw InitializationException("`@Path` not set")

        if (!dateTimeSet)
            throw InitializationException("`@DateTime` not set")

        if (!langSet)
            throw InitializationException("`@Lang` not set")

        structList = parser!!.parseFromFile(file, charset, readDateTimeFormatter!!, writeDateTimeFormatter!!)
    }

    fun insert(record: Record): String = compiler!!.insert(record)

    fun insertThenGetId(record: Record): String = compiler!!.insert(record, true)

    fun updateById(record: Record) =
            compiler!!.update(record, Record(record, true).also { it["id"] = record["id"]!! })

    fun updateByCondition(target: Record, condition: Record) = compiler!!.update(target, condition)

    fun selectById(condition: Record) =
            compiler!!.select(Record(condition, true).also { it["id"] = condition["id"]!! })

    fun selectByCondition(condition: Record) = compiler!!.select(condition)

    fun selectByIdLimit(condition: Record, offset: Long, size: Long) =
            compiler!!.select(Record(condition, true).also { it["id"] = condition["id"]!! }, offset, size)

    fun selectByConditionLimit(target: Record, offset: Long, size: Long) = compiler!!.select(target, offset, size)

    fun deleteById(condition: Record) =
            compiler!!.delete(Record(condition, true).also { it["id"] = condition["id"]!! })

    fun deleteByCondition(condition: Record) = compiler!!.delete(condition)

    companion object {
        fun insert(sqlHelper: SQLHelper, record: Record) = sqlHelper.insert(record)
        fun insertThenGetId(sqlHelper: SQLHelper, record: Record) = sqlHelper.insertThenGetId(record)
        fun updateById(sqlHelper: SQLHelper, record: Record) = sqlHelper.updateById(record)
        fun updateByCondition(sqlHelper: SQLHelper, target: Record, condition: Record) =
                sqlHelper.updateByCondition(target, condition)

        fun selectById(sqlHelper: SQLHelper, condition: Record) = sqlHelper.selectById(condition)
        fun selectByCondition(sqlHelper: SQLHelper, condition: Record) = sqlHelper.selectByCondition(condition)
        fun selectByIdLimit(sqlHelper: SQLHelper, condition: Record, offset: Long, size: Long) =
                sqlHelper.selectByIdLimit(condition, offset, size)

        fun selectByConditionLimit(sqlHelper: SQLHelper, condition: Record, offset: Long, size: Long) =
                sqlHelper.selectByConditionLimit(condition, offset, size)

        fun deleteById(sqlHelper: SQLHelper, condition: Record) = sqlHelper.deleteById(condition)
        fun deleteByCondition(sqlHelper: SQLHelper, condition: Record) = sqlHelper.deleteByCondition(condition)
    }
}
