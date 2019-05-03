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

open class SelectLimitNotValidException(
        offset: Long, size: Long
) : CompilerException(
        "`offset` and `size` must be positive, input is {offset: $offset, size: $size}"
)
