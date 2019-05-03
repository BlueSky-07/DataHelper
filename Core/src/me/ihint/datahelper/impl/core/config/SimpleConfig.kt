package me.ihint.datahelper.impl.core.config

import me.ihint.datahelper.core.Config
import me.ihint.datahelper.exception.config.ItemAlreadySetException

/**
 * SimpleConfig(Config)
 *
 * one basic implement of Config
 * you cannot change a field's value once you set
 */

class SimpleConfig : Config {
    private val config: HashMap<String, Any>

    constructor() {
        config = HashMap()
    }

    constructor(config: SimpleConfig) {
        this.config = HashMap(config.config)
    }

    override operator fun set(key: String, value: Any) =
            when (config.containsKey(key)) {
                true -> throw ItemAlreadySetException(key)
                else -> config[key] = value
            }

    override fun get(key: String) =
            when (config.containsKey(key)) {
                true -> config[key]
                else -> null
            }

    override fun toString(): String = config.toString()
}
