package me.ihint.datahelper.core

/**
 * Group : class
 *
 * a bundle of different data/fields, with properties
 */

interface Group<T> {
    val bundle: Bundle<T>
    val config: Config
}
