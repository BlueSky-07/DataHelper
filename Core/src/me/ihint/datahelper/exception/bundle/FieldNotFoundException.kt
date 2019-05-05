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

open class FieldNotFoundException(
        fieldName: String, source: String
) : BundleException(
        "no such field named \"$fieldName\" in $source"
)
