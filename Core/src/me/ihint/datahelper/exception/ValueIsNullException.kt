package me.ihint.datahelper.exception

/**
 * DataHelperException
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

open class ValueIsNullException : VerifyNotPassException()