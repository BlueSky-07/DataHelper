package me.ihint.datahelper.exception

/**
 * ConfigException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing with config
 *
 * Solution:
 *      check exception's detail
 */

open class ConfigException(message: String) : DataHelperException(message)
