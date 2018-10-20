package me.ihint.datahelper.core

/**
 * Config : interface
 *
 * field's config, deciding how to handle with data
 * implement it to be a Map<String, Any?> like structure
 */

interface Config {
	/**
	 * set a value for a configured field
	 */
	fun set(key: String, value: Any): Any?
	
	/**
	 * get the value of a configured field
	 */
	operator fun get(key: String): Any?
}