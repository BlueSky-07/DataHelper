package me.ihint.datahelper.exception.config

import me.ihint.datahelper.exception.ConfigException

/**
 * ItemAlreadySetException
 *     : ConfigException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      overwrite an item which has been set
 *
 * Solution:
 *      abort operation
 */

open class ItemAlreadySetException : ConfigException()
