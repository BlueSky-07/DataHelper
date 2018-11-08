package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.DataType
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * TIMESTAMP : DataType
 *
 * config: Config
 *      (FOR READING)
 *           ["read"] : DateTimeFormatter        // reading format
 *
 *      (FOR GENERATING)
 *          ["write"] : DateTimeFormatter        // writing format
 */

object TIMESTAMP : DataType {
	override fun verify(data: Data, allowNull: Boolean): Boolean =
			when (val value: String? = data.value) {
				null -> allowNull
				else -> {
					val config = data.config
					val formatter: DateTimeFormatter = config["read"] as DateTimeFormatter
					try {
						LocalDateTime.parse(value, formatter)
						true
					} catch (e: Exception) {
						false
					}
				}
			}
	
	override fun toEntry(data: Data): Entry =
			if (verify(data, false)) Entry(
					"`${data.fieldName}`",
					"`${LocalDateTime.parse(data.value!!, data.config["read"] as DateTimeFormatter)
							.format(data.config["write"] as DateTimeFormatter)
					}`"
			)
			else when (data.value) {
				null -> throw ValueIsNullException()
				else -> throw VerifyNotPassException()
			}
}