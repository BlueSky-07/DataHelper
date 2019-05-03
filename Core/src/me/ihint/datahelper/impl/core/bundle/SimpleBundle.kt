package me.ihint.datahelper.impl.core.bundle

import me.ihint.datahelper.core.Bundle

/**
 * SimpleBundle<T>(Bundle<T>)
 *     : HashMap<String, T>
 *     : AbstractMap<String, T>(Map<String, T>)
 *
 * directly use HashMap<String, T> to implement Bundle<T>
 */

class SimpleBundle<T> : Bundle<T>, HashMap<String, T>() {
    override fun get(key: String): T? {
        return super.get(key)
    }

    operator fun set(key: String, value: T): T? {
        return super.put(key, value)
    }

    override fun toString(): String = super.toString()
}
