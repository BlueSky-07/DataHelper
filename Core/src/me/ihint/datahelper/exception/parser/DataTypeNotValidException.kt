package me.ihint.datahelper.exception.parser

import me.ihint.datahelper.exception.ParserException

/**
 * DataTypeNotValidException
 *     : ParserException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      the fieldName has been defined before
 *
 * Solution:
 *      check the data type
 */

open class DataTypeNotValidException(
        val dataTypeName: String
) : ParserException(
        "\"$dataTypeName\" is not a valid DataType"
)
