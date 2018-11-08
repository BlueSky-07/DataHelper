package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * INTEGER : MysqlDataType(DataType)
 *
 * config: Config
 *      (FOR READING)
 *          ["max"] : Int?        // value should be less than it, not included itself
 *          ["min"] : Int?        // value should be greater then it, included itself
 *
 *      (FOR GENERATING)
 */

object INTEGER : MysqlDataType() {
	override fun verify(data: Data, allowNull: Boolean): Boolean =
			when (val value: String? = data.value) {
				null -> allowNull
				else -> {
					val config = data.config
					val number: Int? = try {
						Integer.valueOf(value)
					} catch (e: Exception) {
						null
					}
					when (number) {
						null -> false
						else -> {
							val max: Int? = config["max"] as Int?
							val min: Int? = config["min"] as Int?
							when {
								(max != null && number >= max) || (min != null && number < min) -> false
								else -> true
							}
						}
					}
				}
			}
	
	
	override fun toEntry(data: Data): Entry =
			if (verify(data, false))
				Entry(
						"`${data.fieldName}`",
						data.value!!
				)
			else when (data.value) {
				null -> throw ValueIsNullException()
				else -> throw VerifyNotPassException()
			}
	
}