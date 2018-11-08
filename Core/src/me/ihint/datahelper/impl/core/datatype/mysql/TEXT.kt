package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.DataType
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * TEXT : DataType
 *
 * config: Config
 *      (FOR READING)
 *          ["max"] : Int?            // the max length of value
 *          ["min"] : Int?            // the min length of value
 *
 *      (FOR GENERATING)
 */

object TEXT : DataType {
	override fun verify(data: Data, allowNull: Boolean): Boolean =
			when (val value: String? = data.value) {
				null -> allowNull
				else -> {
					val config = data.config
					val max: Int? = config["max"] as Int?
					val min: Int? = config["min"] as Int?
					val length = value.length
					when {
						(max != null && length >= max) || (min != null && length < min) -> false
						else -> true
					}
				}
			}
	
	override fun toEntry(data: Data): Entry =
			if (verify(data, false)) Entry(
					"`${data.fieldName}`",
					"`${data.value!!.replace("\\", "\\\\")}`"
			)
			else when (data.value) {
				null -> throw ValueIsNullException()
				else -> throw VerifyNotPassException()
			}
}