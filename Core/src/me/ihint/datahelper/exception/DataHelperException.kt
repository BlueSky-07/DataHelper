package me.ihint.datahelper.exception

import java.lang.Exception

/**
 * DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing data
 *
 * Solution:
 *      check exception's detail
 */
open class DataHelperException : Exception()