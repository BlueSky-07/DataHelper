package me.ihint.datahelper.exception.parser

import me.ihint.datahelper.exception.ParserException

/**
 * MultipleDefinitionException
 *     : ParserException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      the fieldName has been defined before
 *
 * Solution:
 *      change the fieldName
 */

open class MultipleDefinitionException(
        val fieldName: String
) : ParserException(
        "`$fieldName` has been defined before"
)
