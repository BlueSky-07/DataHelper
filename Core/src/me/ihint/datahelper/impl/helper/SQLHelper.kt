package me.ihint.datahelper.impl.helper

import me.ihint.datahelper.DataHelper
import me.ihint.datahelper.annotation.DateTime
import me.ihint.datahelper.annotation.Lang
import me.ihint.datahelper.annotation.File
import me.ihint.datahelper.compiler.SQLCompiler
import me.ihint.datahelper.parser.SQLParser
import me.ihint.datahelper.core.Bundle
import me.ihint.datahelper.exception.runtime.InitializationException
import me.ihint.datahelper.impl.core.group.mysql.Record
import me.ihint.datahelper.impl.core.group.mysql.Struct
import java.time.format.DateTimeFormatter

class SQLHelper : DataHelper<SQLCompiler<Record>, SQLParser<Struct>> {
    // flag for avoid repeat settings
    private var pathSet = false
    private var dateTimeSet = false
    private var langSet = false

    // @File
    var inputFilePath: String? = null
    var inputFileCharset: String? = null

    // @DateTime
    var dateTimeFormatterInput: DateTimeFormatter? = null
    var dateTimeFormatterOutput: DateTimeFormatter? = null

    // SQLCompiler & SQLParser
    public override var compiler: SQLCompiler<Record>? = null
    public override var parser: SQLParser<Struct>? = null

    constructor()

    constructor(
            inputFilePath: String,
            inputFileCharset: String,
            dateTimeFormatterInput: DateTimeFormatter,
            dateTimeFormatterOutput: DateTimeFormatter,
            compiler: SQLCompiler<Record>,
            parser: SQLParser<Struct>
    ) : this() {
        this.inputFilePath = inputFilePath
        this.inputFileCharset = inputFileCharset
        this.dateTimeFormatterInput = dateTimeFormatterInput
        this.dateTimeFormatterOutput = dateTimeFormatterOutput
        this.compiler = compiler
        this.parser = parser
    }

    // Struct List
    var structList: Bundle<Struct>? = null

    /**
     * get struct by tableName
     *
     * null will be returned if it does not exist
     */
    operator fun get(tableName: String): Struct? = structList!![tableName]

    /**
     * init helper by annotation
     */
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
                        File::class -> {
                            if (pathSet) {
                                throw InitializationException("`@File` has already been set")
                            }
                            val a = annotation as File
                            inputFilePath = a.path
                            inputFileCharset = a.charset
                            pathSet = true
                        }
                        DateTime::class -> {
                            if (dateTimeSet) {
                                throw InitializationException("`@DateTime` has already been set")
                            }
                            val a = annotation as DateTime
                            dateTimeFormatterInput = DateTimeFormatter.ofPattern(a.input)
                            dateTimeFormatterOutput = DateTimeFormatter.ofPattern(a.output)
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
            throw InitializationException("`@File` not set")

        if (!dateTimeSet)
            throw InitializationException("`@DateTime` not set")

        if (!langSet)
            throw InitializationException("`@Lang` not set")

        init()
    }

    fun init() {
        structList = parser!!.parseFromFile(inputFilePath!!, inputFileCharset!!, dateTimeFormatterInput!!, dateTimeFormatterOutput!!)
        isInited = true
    }

    fun insert(record: Record): String = compiler!!.insert(record)

    fun insertThenGetId(record: Record): String = compiler!!.insert(record, true)

    fun updateById(record: Record): String =
            compiler!!.update(record, Record(record, true).also { it["id"] = record["id"]!! })

    fun updateByCondition(target: Record, condition: Record): String = compiler!!.update(target, condition)

    fun selectById(condition: Record): String =
            compiler!!.select(Record(condition, true).also { it["id"] = condition["id"]!! })

    fun selectByCondition(condition: Record): String = compiler!!.select(condition)

    fun selectByIdLimit(condition: Record, offset: Long, size: Long): String =
            compiler!!.select(Record(condition, true).also { it["id"] = condition["id"]!! }, offset, size)

    fun selectByConditionLimit(target: Record, offset: Long, size: Long): String = compiler!!.select(target, offset, size)

    fun deleteById(condition: Record): String =
            compiler!!.delete(Record(condition, true).also { it["id"] = condition["id"]!! })

    fun deleteByCondition(condition: Record): String = compiler!!.delete(condition)
}
