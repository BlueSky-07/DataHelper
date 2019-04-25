package me.ihint.datahelper.exception

import java.lang.Exception

/**
 * DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing with data
 *
 * Solution:
 *      check exception's detail
 */

open class DataHelperException : Exception()
