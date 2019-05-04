package me.ihint.datahelper.core

/**
 * Entry : class
 *
 * a combination of data's key and value
 * implement it to be a Map.Entry like structure
 */

open class Entry(val key: String, val value: String) {
    override fun toString(): String {
        return "{$key: $value}"
    }
}
