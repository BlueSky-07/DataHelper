package me.ihint.datahelper.exception.compiler

import me.ihint.datahelper.exception.CompilerException

/**
 * RecordIsEmptyException
 *     : CompilerException
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

open class RecordIsEmptyException : CompilerException()
