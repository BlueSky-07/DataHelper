package me.ihint.datahelper.exception.config

import me.ihint.datahelper.exception.ConfigException

/**
 * ItemNotFoundException
 *     : ConfigException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      access an unset item
 *
 * Solution:
 *      check validity of item name
 */

open class ItemNotFoundException : ConfigException()
