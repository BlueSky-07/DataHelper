package me.ihint.datahelper.exception

import me.ihint.datahelper.core.DataType

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

open class VerifyNotPassException(
        value: String?,
        dataType: DataType
) : DataHelperException(
        "${
        when(value) {
            null -> "null"
            else -> "\"$value\""
        }
        } is not a valid value of ${dataType.javaClass.name}"
)
