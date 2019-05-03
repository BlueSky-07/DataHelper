package me.ihint.datahelper.exception.runtime

import me.ihint.datahelper.exception.DataHelperException

/**
 * InitializationException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing with initialization
 *
 * Solution:
 *      check annotation setting
 */

open class InitializationException(message: String) : DataHelperException(message)
