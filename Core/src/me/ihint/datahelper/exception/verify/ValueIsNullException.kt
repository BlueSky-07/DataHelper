package me.ihint.datahelper.exception.verify

import me.ihint.datahelper.core.DataType
import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * ValueIsNullException
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

open class ValueIsNullException(
        dataType: DataType
) : VerifyNotPassException(
        null, dataType
)
