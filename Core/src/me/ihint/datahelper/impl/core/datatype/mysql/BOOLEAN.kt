package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.DataType
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * BOOLEAN : DataType
 *
 * config: Config
 *      (FOR READING)
 *          it will recognize: "true", "false", "1", "0"
 *
 *      (FOR GENERATING)
 *          it will use: "TRUE", "FALSE"
 */

object BOOLEAN : DataType {
	override fun verify(data: Data, allowNull: Boolean): Boolean =
			when (data.value) {
				null -> allowNull
				else -> {
					val str = data.value!!.toLowerCase()
					when (str) {
						"true", "false", "1", "0" -> true
						else -> false
					}
				}
			}
	
	override fun toEntry(data: Data): Entry =
			if (verify(data, false))
				Entry(
						"`${data.fieldName}`",
						when (data.value!!.toLowerCase()) {
							"1", "true" -> "TRUE"
							"0", "false" -> "FALSE"
							else -> throw VerifyNotPassException()
						})
			else when (data.value) {
				null -> throw ValueIsNullException()
				else -> throw VerifyNotPassException()
			}
}