package me.ihint.datahelper.exception

import me.ihint.datahelper.exception.DataHelperException

/**
 * VerifyNotPassException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      value of a data is invalid
 *
 * Solution:
 *      check value
 */

open class VerifyNotPassException : DataHelperException()
