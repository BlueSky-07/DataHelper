package me.ihint.datahelper.exception

import me.ihint.datahelper.exception.DataHelperException

/**
 * BundleException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing bundle
 *
 * Solution:
 *      check exception's detail
 */

open class BundleException : DataHelperException()
