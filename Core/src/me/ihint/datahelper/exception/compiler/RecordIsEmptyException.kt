package me.ihint.datahelper.exception.compiler

import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * RecordIsEmptyException
 *     : VerifyNotPassException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      all data in record is null
 *
 * Solution:
 *      set at least one data before
 */

open class RecordIsEmptyException : VerifyNotPassException()
