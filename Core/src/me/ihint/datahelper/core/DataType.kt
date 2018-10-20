package me.ihint.datahelper.core

/**
 * DataType : interface
 *
 * type of an kind of data
 */

interface DataType {
	/**
	 * check validity of data's value
	 */
	fun verify(data: Data, allowNull: Boolean): Boolean
	
	/**
	 * generate an Entry for data whose value is not null
	 */
	fun toEntry(data: Data): Entry
}