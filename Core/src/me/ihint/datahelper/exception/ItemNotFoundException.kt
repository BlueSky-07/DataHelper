package me.ihint.datahelper.exception

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