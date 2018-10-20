package me.ihint.datahelper.impl.core.config

import me.ihint.datahelper.core.Config
import me.ihint.datahelper.exception.DataHelperException

/**
 * SimpleConfig(Config)
 *
 * one basic implement of Config
 * you cannot change a field's value once you set
 */

class SimpleConfig : Config {
	private val config = HashMap<String, Any>()
	
	override operator fun set(key: String, value: Any) =
			when (config.containsKey(key)) {
				true -> throw ConfigAlraadySetException()
				else -> config[key] = value
			}
	
	override fun get(key: String) =
			when (config.containsKey(key)) {
				true -> config[key]
				else -> null
			}
	
	class ConfigAlraadySetException : DataHelperException()
}