package me.ihint.datahelper.exception.compiler

import me.ihint.datahelper.exception.CompilerException

/**
 * SelectLimitNotValidException
 *     : CompilerException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      limit or size must be positive
 *
 * Solution:
 *      check limit or size
 */

open class SelectLimitNotValidException : CompilerException()
