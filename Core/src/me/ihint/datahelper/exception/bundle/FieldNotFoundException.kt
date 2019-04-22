package me.ihint.datahelper.exception.bundle

import me.ihint.datahelper.exception.BundleException

/**
 * FieldNotFoundException
 *     : BundleException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      access an unset field
 *
 * Solution:
 *      check validity of field name
 */

open class FieldNotFoundException : BundleException()