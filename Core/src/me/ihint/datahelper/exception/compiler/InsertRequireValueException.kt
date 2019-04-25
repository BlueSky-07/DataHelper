package me.ihint.datahelper.exception.compiler

import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * InsertRequireValueException
 *     : VerifyNotPassException
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

open class InsertRequireValueException : VerifyNotPassException()
