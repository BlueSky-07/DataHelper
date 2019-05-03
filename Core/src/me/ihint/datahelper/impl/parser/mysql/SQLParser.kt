package me.ihint.datahelper.impl.parser.mysql

import me.ihint.datahelper.core.Bundle
import me.ihint.datahelper.core.DataType
import me.ihint.datahelper.core.Field
import me.ihint.datahelper.exception.parser.DataTypeNotValidException
import me.ihint.datahelper.exception.parser.MultipleDefinitionException
import me.ihint.datahelper.impl.core.bundle.SimpleBundle
import me.ihint.datahelper.impl.core.config.SimpleConfig
import me.ihint.datahelper.impl.core.datatype.mysql.*
import me.ihint.datahelper.impl.core.group.mysql.Struct
import me.ihint.datahelper.exception.ParserException
import java.io.File
import java.io.FileNotFoundException
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

class SQLParser(
        val location: String // caller's name & property name
) {
    private var structList: SimpleBundle<Struct> = SimpleBundle()

    // input scanner
    private var input: Scanner? = null

    // current line
    private var lineIndex: Int = 0
    private var line: String? = null

    // current table
    private var tableName: String? = null
    private var fieldList: SimpleBundle<Field>? = null
    private var orderList: ArrayList<Field>? = null
    private var requiredList: ArrayList<Field>? = null
    private var isTableReading: Boolean = false

    // DateTimeFormatter
    private var readDateTimeFormatter: DateTimeFormatter? = null
    private var writeDateTimeFormatter: DateTimeFormatter? = null

    private fun getNextLine() {
        ++lineIndex
        if (!input!!.hasNext()) {
            line = null
            return
        }
        val _line = input!!.nextLine().trim()
        return when {
            _line.isEmpty() || _line.startsWith(COMMENT) -> getNextLine()
            else -> line = _line

        }
    }

    public fun parseFromFile(filePath: String, charset: String, readDateTimeFormatter: DateTimeFormatter, writeDateTimeFormatter: DateTimeFormatter): Bundle<Struct> {
        setInputPath(filePath, charset)
        this.readDateTimeFormatter = readDateTimeFormatter
        this.writeDateTimeFormatter = writeDateTimeFormatter

        structList = SimpleBundle()

        tableName = ""
        fieldList = SimpleBundle()
        orderList = ArrayList()
        requiredList = ArrayList()
        isTableReading = false

        lineIndex = 0
        getNextLine()
        while (line != null) {
            parse()
            getNextLine()
        }

        input!!.close()

        return structList
    }

    private fun setInputPath(filePath: String, charset: String) {
        var path = filePath
        if (filePath.startsWith("classpath:")) {
            path = filePath.substring("classpath:".length)
            var cl: ClassLoader?
            try {
                cl = Thread.currentThread().contextClassLoader
                if (cl == null) {
                    cl = this.javaClass.classLoader
                    if (cl == null) {
                        cl = ClassLoader.getSystemClassLoader()
                    }
                }
                path = cl!!.getResource("")!!.path + path
            } catch (e: Exception) {
                throw FileNotFoundException()
            }
        }

        input = Scanner(File(path), charset)
    }

    private fun parse() {
        if (line!!.endsWith(LEFT)) {
            if (isTableReading) {
                throwError(ParserException("last table has not read }"))
            } else {
                tableName = line!!.trim().replace(LEFT, "").trim()
                if (tableName!!.isEmpty()) {
                    throwError(ParserException("tableName must be set"))
                }
                isTableReading = true
            }
        } else if (line!!.startsWith(RIGHT)) {
            if (!isTableReading) {
                throwError(ParserException("useless }"))
            } else {
                if (!fieldList!!.containsKey("id")) {
                    throwError(ParserException("`$tableName` must have `id`(INTEGER) field"))
                }
                val struct = Struct(fieldList as SimpleBundle<Field>, SimpleConfig())
                struct.setTableName(tableName!!)
                struct.setRequiredList(requiredList!!)
                struct.setOrderList(orderList!!)
                structList[tableName!!] = struct

                fieldList = SimpleBundle()
                orderList = ArrayList()
                requiredList = ArrayList()
                isTableReading = false
            }
        } else {
            try {
                val fieldNameAndDataTypeName = line!!.split(AT)[0].trim()
                val fieldName = fieldNameAndDataTypeName.split(DEFINITION)[0].trim()
                if (fieldList!!.containsKey(fieldName)) {
                    throwError(MultipleDefinitionException(fieldName))
                }
                val dataTypeName = fieldNameAndDataTypeName.split(DEFINITION)[1].split("-".toRegex())[0].trim()
                val dataType: DataType? = when (dataTypeName.toUpperCase()) {
                    "INTEGER" -> INTEGER
                    "VARCHAR" -> VARCHAR
                    "TEXT" -> TEXT
                    "BOOLEAN" -> BOOLEAN
                    "TIMESTAMP" -> TIMESTAMP
                    "DOUBLE" -> DOUBLE
                    else -> {
                        throwError(DataTypeNotValidException(dataTypeName))
                        null
                    }
                }
                val field = Field(fieldName, dataType!!, SimpleConfig())

                val required = line!!.contains(REQUIRED)
                if (required) {
                    field.config["required"] = true
                    requiredList!!.add(field)
                }

                when (val order = getConfigOfField(ORDER)) {
                    "ASC", "DESC" -> {
                        field.config["order"] = order.toUpperCase()
                        orderList!!.add(field)
                    }
                    else -> {}
                }

                try {
                    when (val pattern = getConfigOfField(VERIFY)) {
                        null -> {}
                        else -> field.config["pattern"] = Pattern.compile(pattern)
                    }
                } catch (e: PatternSyntaxException) {
                    throwError(ParserException("pattern set by @Verify is not valid"))
                }

                try {
                    when (val min = getConfigOfField(MIN)) {
                        null -> {}
                        else -> {
                            when(dataType) {
                                INTEGER -> field.config["min"] = Integer.valueOf(min)
                                DOUBLE -> field.config["min"] = java.lang.Double.valueOf(min)
                            }
                        }
                    }
                } catch (e: NumberFormatException) {
                    throwError(ParserException("minimum number set by @Min is not valid"))
                }

                try {
                    when (val max = getConfigOfField(MAX)) {
                        null -> {}
                        else -> {
                            when(dataType) {
                                INTEGER -> field.config["max"] = Integer.valueOf(max)
                                DOUBLE -> field.config["max"] = java.lang.Double.valueOf(max)
                            }
                        }
                    }
                } catch (e: NumberFormatException) {
                    throwError(ParserException("maximum number set by @Max is not valid"))
                }

                try {
                    when (val fix = getConfigOfField(FIX)) {
                        null -> {}
                        else -> {
                            when(dataType) {
                                DOUBLE -> field.config["fix"] = Integer.valueOf(fix)
                            }
                        }
                    }
                } catch (e: NumberFormatException) {
                    throwError(ParserException("number of decimals set by @Fix is not valid"))
                }

                try {
                    when (val length = getConfigOfField(LENGTH)) {
                        null -> {}
                        else -> {
                            when(dataType) {
                                VARCHAR -> field.config["length"] = Integer.valueOf(length)
                            }
                        }
                    }
                } catch (e: NumberFormatException) {
                    throwError(ParserException("number of varchar's length set by @Length is not valid"))
                }

                if (dataType is VARCHAR && field.config["length"] == null) {
                    throwError(ParserException("field of VARCHAR must set `length`"))
                }

                if (dataType is TIMESTAMP) {
                    field.config["read"] = readDateTimeFormatter!!
                    field.config["write"] = writeDateTimeFormatter!!
                }

                fieldList!![fieldName] = field
            } catch (e: ParserException) {
                throwError(e)
            } catch (e: Exception) {
                throwError(ParserException("unable to parse"))
            }
        }
    }

    private fun getConfigOfField(atSymbol: String): String? {
        val p = Pattern.compile("$atSymbol ([^ ]*)")
        val m = p.matcher(line!!)
        return if (m.find()) m.group().substring(atSymbol.length).trim()
        else null
    }

    private fun throwError(error: ParserException) {
        var message = ""
        val locationPrefix = if (location.isEmpty()) "$location：" else ""
        message += locationPrefix
        if (lineIndex != 0) {
            message += "Line $lineIndex：${error.message}"
        }
        error.message = message
        throw error
    }

    companion object {
        private val COMMENT = "#"
        private val DEFINITION = ":"
        private val LEFT = "{"
        private val RIGHT = "}"
        private val AT = "@"
        private val REQUIRED = "@Required"
        private val ORDER = "@Order"
        private val VERIFY = "@Verify"
        private val MIN = "@Min"
        private val MAX = "@Max"
        private val FIX = "@Fix"
        private val LENGTH = "@Length"
    }
}
