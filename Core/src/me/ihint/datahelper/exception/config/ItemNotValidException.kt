package me.ihint.datahelper.exception.config

import me.ihint.datahelper.exception.ConfigException

/**
 * ItemNotValidException
 *     : ConfigException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      value of item is invalid
 *
 * Solution:
 *      check validity of item value
 */

open class ItemNotValidException : ConfigException()