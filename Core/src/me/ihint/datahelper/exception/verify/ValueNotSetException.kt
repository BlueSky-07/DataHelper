package me.ihint.datahelper.exception.verify

import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * ValueNotSetException
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

open class ValueNotSetException : VerifyNotPassException()
