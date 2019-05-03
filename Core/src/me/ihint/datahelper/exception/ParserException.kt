package me.ihint.datahelper.exception

/**
 * ParserException
 *     : DataHelperException
 *     : Exception
 *     : Throwable(Serializable)
 *
 * When thrown:
 *      something wrong when processing with parser
 *
 * Solution:
 *      check text input
 */

open class ParserException(
        override var message: String
) : DataHelperException(message)
