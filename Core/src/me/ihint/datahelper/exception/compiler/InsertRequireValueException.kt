package me.ihint.datahelper.exception.compiler

import me.ihint.datahelper.exception.CompilerException

/**
 * InsertRequireValueException
 *     : CompilerException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      the required value is null
 *
 * Solution:
 *      set value before using it
 */

open class InsertRequireValueException(
        fieldName: String
) : CompilerException(
        "value of `$fieldName` must be set"
)
