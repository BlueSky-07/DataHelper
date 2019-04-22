package me.ihint.datahelper.exception

import me.ihint.datahelper.exception.DataHelperException

/**
 * ConfigException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing config
 *
 * Solution:
 *      check exception's detail
 */

open class ConfigException : DataHelperException()
